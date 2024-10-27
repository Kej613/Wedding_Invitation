package spring.backend.wedding_invitation.Service;

import spring.backend.wedding_invitation.Domain.User;
import spring.backend.wedding_invitation.Dto.UserRequestDTO;
import spring.backend.wedding_invitation.Dto.UserResponseDTO;
import spring.backend.wedding_invitation.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /** User 조회 */
    @Transactional
    public UserResponseDTO findById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + id));
        return new UserResponseDTO(user);
    }

    /** User 수정 */
    @Transactional
    public void update(Long id, UserRequestDTO requestDto) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + id));
        user.update(requestDto);
    }

    /** User 삭제 */
    @Transactional
    public void delete(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + id));
        this.userRepository.delete(user);
    }
}
