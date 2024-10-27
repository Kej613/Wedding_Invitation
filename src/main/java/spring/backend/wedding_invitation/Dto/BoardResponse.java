package spring.backend.wedding_invitation.Dto;

import spring.backend.wedding_invitation.Domain.Board;
import lombok.Getter;

@Getter
public class BoardResponse {
    private final String title;
    private final String content;

    public BoardResponse(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
