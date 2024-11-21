package user.dto;

import lombok.Getter;

@Getter
public class UpdateCommentsRequestDto {
    private final long boardId;
    private final long userId;
    private final String comment;

    public UpdateCommentsRequestDto(long boardId, long userId, String comment) {
    this.boardId = boardId;
    this.userId = userId;
    this.comment = comment;
    }

}