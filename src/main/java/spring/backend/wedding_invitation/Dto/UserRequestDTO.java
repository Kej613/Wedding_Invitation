package spring.backend.wedding_invitation.Dto;

import spring.backend.wedding_invitation.Domain.Role;
import spring.backend.wedding_invitation.Domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
    회원가입 및 로그인에 사용되는 로그인 객체
    DB 생성 요청이 발생할 경우, DTO 객체에서 Entity 객체로 변활할 toEntity() 메소드 구현
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private Role role;
    private String email;
    private String contact;
    private String username;
    private String password;

    public User toEntity() {
        return User.builder()
                .role(this.role)
                .email(this.email)
                .contact(this.contact)
                .username(this.username)
                .password(this.password)
                .build();
    }
}
