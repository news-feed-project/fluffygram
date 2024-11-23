package com.fluffygram.newsfeed.domain.comment.dto;

import lombok.Getter;
import com.fluffygram.newsfeed.domain.comment.entity.Comments;

import java.time.LocalDateTime;

@Getter
public class CommentsResponseDto {
    private final Long id;
    private final Long boardId;
    private final String comment;
    private final String userNickname;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifyAt;


    public CommentsResponseDto(Long id, Long boardId, String comment, String userNickname, LocalDateTime createdAt, LocalDateTime modifyAt) {
        this.id = id;
        this.boardId = boardId;
        this.comment = comment;
        this.userNickname = userNickname;
        this.createdAt = createdAt;
        this.modifyAt = modifyAt;
    }

    public static CommentsResponseDto toDto(Comments comments) {
        return new CommentsResponseDto(
                comments.getId(),
                comments.getBoard().getId(),
                comments.getComment(),
                comments.getUser().getUserNickname(),
                comments.getCreatedAt(),
                comments.getModifiedAt()
        );
    }
}
