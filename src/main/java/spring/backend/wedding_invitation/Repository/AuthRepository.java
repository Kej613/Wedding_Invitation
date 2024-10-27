package spring.backend.wedding_invitation.Repository;

import spring.backend.wedding_invitation.Domain.Auth;
import spring.backend.wedding_invitation.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, String> {
    boolean existsByUser(User user);
    Optional<Auth> findByRefreshToken(String refreshToken);
}
