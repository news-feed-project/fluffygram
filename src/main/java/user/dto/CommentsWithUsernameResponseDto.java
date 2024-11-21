package user.dto;

import lombok.Getter;

@Getter
public class CommentsWithUsernameResponseDto {
    private final long boardId;
    private final long userId;
    private final String comment;

    public CommentsWithUsernameResponseDto(long boardId, long userId, String comment) {
        this.boardId = boardId;
        this.userId = userId;
        this.comment = comment;
    }
}
