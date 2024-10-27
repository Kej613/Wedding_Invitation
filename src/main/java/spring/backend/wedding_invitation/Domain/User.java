package spring.backend.wedding_invitation.Domain;

import spring.backend.wedding_invitation.Domain.Auth;
import spring.backend.wedding_invitation.Dto.UserRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length= 50, nullable = false, unique = true)
    private String email;

    @Column(length= 50, nullable = false, unique = true)
    private String contact;

    @Column(length= 50, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Auth auth;

    @Builder
    public User(Role role, String email, String contact, String username, String password) {
        this.email = email;
        this.role = role;
        this.contact = contact;
        this.username = username;
        this.password = password;
    }

    public void update(UserRequestDTO requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
    }
}
