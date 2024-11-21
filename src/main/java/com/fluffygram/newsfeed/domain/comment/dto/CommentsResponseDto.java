package com.fluffygram.newsfeed.domain.comment.dto;

import lombok.Getter;
import com.fluffygram.newsfeed.domain.comment.entity.Comments;

import java.time.LocalDateTime;

@Getter
public class CommentsResponseDto {
    private Long id;
    private Long BoardId;
    private String comment;
    private LocalDateTime createdat;
    private LocalDateTime modifyat;


    public CommentsResponseDto(Comments comments) {
        this.id = comments.getId();
        this.BoardId = comments.getBoardId();
        this.comment = comments.getComment();
    }
    public static CommentsResponseDto toDto(Comments comments) {
        return new CommentsResponseDto(comments.getId(), comments.getBoardId(), comments.getComment());
    }
}
