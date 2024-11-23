package com.fluffygram.newsfeed.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.fluffygram.newsfeed.domain.comment.entity.Comments;

import java.time.LocalDateTime;

@Getter

public class CommentsResponseDto {
    private final Long id;
    private final Long boardId;
    private final String comment;
    private final String userNickname;
    private final LocalDateTime createdat;
    private final LocalDateTime modifyat;


    public CommentsResponseDto(Long id, Long boardId, String comment, String userNickname, LocalDateTime createdat, LocalDateTime modifyat) {
        this.id = id;
        this.boardId = boardId;
        this.comment = comment;
        this.userNickname = userNickname;
        this.createdat = createdat;
        this.modifyat = modifyat;
    }

    public static CommentsResponseDto toDto(Comments comments) {
        return new CommentsResponseDto(
                comments.getId(),
                comments.getBoard().getId(),
                comments.getComment(),
                comments.getUserNickname(),
                comments.getCreatedAt(),
                comments.getModifyAt());
    }
}
