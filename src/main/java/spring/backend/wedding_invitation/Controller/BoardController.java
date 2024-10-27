package spring.backend.wedding_invitation.Controller;

import spring.backend.wedding_invitation.Domain.Board;
import spring.backend.wedding_invitation.Dto.AddBoardRequest;
import spring.backend.wedding_invitation.Dto.BoardResponse;
import spring.backend.wedding_invitation.Dto.UpdateBoardRequest;
import spring.backend.wedding_invitation.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/Boards")
    public ResponseEntity<Board> addBoard(@RequestBody AddBoardRequest request) {
        Board savedBoard = boardService.save(request);

        // Board savedBoard = BoardRepository.save(request.toEntity()); 

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedBoard);
    }

    @GetMapping("/api/Boards")
    public ResponseEntity<List<Board>> findAllBoards() {
        List<Board> Boards = boardService.findAll();
        return ResponseEntity.ok()
                .body(Boards);
    }

    @GetMapping("/api/Boards/{id}")
    public ResponseEntity<BoardResponse> findBoard(@PathVariable long id) {
        Board Board = boardService.findById(id);

        return ResponseEntity.ok()
                .body(new BoardResponse(Board));
    }

    @DeleteMapping("/api/Boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable long id) {
        boardService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/Boards/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable long id,
                                                 @RequestBody UpdateBoardRequest request) {
        Board updatedBoard = boardService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedBoard);
    }
}

