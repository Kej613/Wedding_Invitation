package spring.backend.wedding_invitation.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
   클라이언트측에서 서버측으로 API 요청을 보낼 때 사용될 DTO
   서버측 API는 사용자를 인증하고 accessToken을 생성하기 위해 username, password 를 받음
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    private String username;
    private String password;
}
