package com.fluffygram.newsfeed.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.fluffygram.newsfeed.domain.comment.entity.Comments;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentsResponseDto {
    private final Long id;
    private final Long boardId;
    private final String comment;
    private final String userNickname;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifyAt;


    public CommentsResponseDto(Comments comments) {
        this.id = comments.getId();
        this.boardId = comments.getBoard().getId();
        this.userNickname = comments.getComment();
        this.comment = comments.getComment();
        this.createdAt = comments.getCreatedAt();
        this.modifyAt = comments.getModifiedAt();
    }


    public static CommentsResponseDto toDto(Comments comments) {
        return new CommentsResponseDto(comments.getId(), comments.getBoard().getId(), comments.getComment(), comments.getUser().getUserNickname(),comments.getCreatedAt(), comments.getModifiedAt());
    }
}
