package spring.backend.wedding_invitation.Repository;

import spring.backend.wedding_invitation.Domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
}
