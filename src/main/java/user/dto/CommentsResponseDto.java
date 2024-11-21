package user.dto;

import lombok.Getter;
import user.entity.Comments;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CommentsResponseDto {
    private Long id;
    private Long boardid;
    private String usernickname;
    private String comment;
    private LocalDateTime createdat;
    private LocalDateTime modifyat;


    public CommentsResponseDto(Comments comments) {
        this.id = comments.getId();
        this.boardid = comments.getBoardId();
        this.usernickname = comments.getUserNickName();
        this.comment = comments.getComment();
    }
}
