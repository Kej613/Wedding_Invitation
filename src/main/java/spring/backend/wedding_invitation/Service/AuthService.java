package spring.backend.wedding_invitation.Service;

import spring.backend.wedding_invitation.Config.CustomUserDetails;
import spring.backend.wedding_invitation.Config.JwtTokenProvider;
import spring.backend.wedding_invitation.Domain.Auth;
import spring.backend.wedding_invitation.Domain.Role;
import spring.backend.wedding_invitation.Domain.User;
import spring.backend.wedding_invitation.Dto.AuthRequestDTO;
import spring.backend.wedding_invitation.Dto.AuthResponseDTO;
import spring.backend.wedding_invitation.Dto.UserRequestDTO;
import spring.backend.wedding_invitation.Repository.AuthRepository;
import spring.backend.wedding_invitation.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
   로그인 및 회원가입, 토큰의 만료기간에 관여
   1. 사용자가 입력한 아이디 존재여부 확인, 비밀번호 일치여부 확인
   2. ACCESS_TOKEN, REFRESH_TOKEN을 생성, USER와 연결된 AUTH 가 존재여부 확인
   3. USER와 연결된 AUTH가 있다면, ACCESS_TOKEN, REFRESH_TOKEN을 업데이트
   4. USER와 연결된 AUTH가 없다면, 새 AUTH 데이터 저장
   5. AuthResponseDto return
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /** 로그인 */
    @Transactional
    public AuthResponseDTO login(AuthRequestDTO requestDto) {
        // 유저네임과 패스워드 확인
        User user = this.userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다. username = " + requestDto.getUsername()));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. username = " + requestDto.getUsername());
        }

        // Access 토큰 과 refresh 토큰 생성
        String accessToken = this.jwtTokenProvider.generateAccessToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.getPassword()));
        String refreshToken = this.jwtTokenProvider.generateRefreshToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.getPassword()));

        // 인가된 사용자가 존재하는지 확인, 존재하면 UPDATE TOKEN
        if (this.authRepository.existsByUser(user)) {
            user.getAuth().updateAccessToken(accessToken);
            user.getAuth().updateRefreshToken(refreshToken);
            return new AuthResponseDTO(user.getAuth());
        }

        // 인가된 사용자가 존재하지 않으면, 인가된 사용자 정보를 저장하고 토큰부여
        Auth auth = this.authRepository.save(Auth.builder()
                .user(user)
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
        return new AuthResponseDTO(auth);
    }

    /** 회원가입 */
    @Transactional
    public void signup(UserRequestDTO requestDto) {
        // 사용자 정보 저장
        requestDto.setRole(Role.ROLE_USER);
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        this.userRepository.save(requestDto.toEntity());
    }

    /** Token 갱신 */
    /**
     1. REFRESH_TOKEN이 유효한지 확인
     2. 유효하다면, REFRESH_TOKEN을 통해 해당 AUTH 데이터 찾기
     3. 신규 ACCESS_TOKEN을 생성하고, 미리 찾은 AUTH 데이터에 업데이트
     4. NEW_ACCESS_TOKEN return */
    @Transactional
    public String refreshToken(String refreshToken) {
        if (this.jwtTokenProvider.validateToken(refreshToken)) {
            Auth auth = this.authRepository.findByRefreshToken(refreshToken).orElseThrow(
                    () -> new IllegalArgumentException("해당 REFRESH_TOKEN 을 찾을 수 없습니다.\nREFRESH_TOKEN = " + refreshToken));

            String newAccessToken = this.jwtTokenProvider.generateAccessToken(
                    new UsernamePasswordAuthenticationToken(
                            new CustomUserDetails(auth.getUser()), auth.getUser().getPassword()));
            auth.updateAccessToken(newAccessToken);
            return newAccessToken;
        }

        // IF NOT AVAILABLE REFRESH_TOKEN EXPIRATION, REGENERATE ACCESS_TOKEN AND REFRESH_TOKEN
        // IN THIS CASE, USER HAVE TO LOGIN AGAIN, SO REGENERATE IS NOT APPROPRIATE
        return null;
    }
}
