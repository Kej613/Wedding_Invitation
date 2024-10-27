package spring.backend.wedding_invitation.Dto;

import spring.backend.wedding_invitation.Domain.User;
import lombok.Getter;
import lombok.Setter;

/*
    로그인 후 응답에 사용되는 데이터 객체
 */
@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String role;
    private String email;
    private String contact;
    private String username;

    public UserResponseDTO(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.contact = entity.getContact();
        this.username = entity.getUsername();
        // Enum -> String
        this.role = entity.getRole().name();
    }
}
