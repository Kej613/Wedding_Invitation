package spring.backend.wedding_invitation.Service;

import spring.backend.wedding_invitation.Domain.Board;
import spring.backend.wedding_invitation.Dto.AddBoardRequest;
import spring.backend.wedding_invitation.Dto.UpdateBoardRequest;
import spring.backend.wedding_invitation.Repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;


    // 저장
    public Board save(AddBoardRequest request) {
        return boardRepository.save(request.toEntity());
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findById(long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    //삭제
    public void delete(long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public Board update(long id, UpdateBoardRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        board.update(request.getTitle(), request.getContent());

        return board;
    }
}
