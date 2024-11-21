package user.dto;

import lombok.Getter;
import user.entity.Comments;

@Getter
public class CommentsResponseDto {
    private final long boardId;
    private final long userId;
    private final String comment;
    //댓글 생성
    public CommentsResponseDto(long boardId, long userId, String comment) {
        this.boardId = boardId;
        this.userId = userId;
        this.comment = comment;
    }

    //댓글 전체 조회
    public static CommentsResponseDto toDto(Comments comment) {
        return new CommentsResponseDto(comment.getboardId(), comment.getuserId(),
                comment.getcomment());
    }
}
