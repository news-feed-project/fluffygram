package user.dto;

import lombok.Getter;

@Getter
public class CreateCommentsRequestDto {
    private final long boardId;
    private final long userId;
    private final String comment;


    public CreateCommentsRequestDto(final long boardId, final long userId, final String comment) {
        this.boardId = boardId;
        this.userId = userId;
        this.comment = comment;
    }
}
