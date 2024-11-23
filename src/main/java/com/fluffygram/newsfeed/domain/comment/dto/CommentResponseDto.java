package com.fluffygram.newsfeed.domain.comment.dto;

import lombok.Getter;
import com.fluffygram.newsfeed.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final Long boardId;
    private final String userNickname;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifyAt;


    public CommentResponseDto(Long id, Long boardId, String comment, String userNickname, LocalDateTime createdAt, LocalDateTime modifyAt) {
        this.id = id;
        this.boardId = boardId;
        this.comment = comment;
        this.userNickname = userNickname;
        this.createdAt = createdAt;
        this.modifyAt = modifyAt;
    }

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getBoard().getId(),
                comment.getComment(),
                comment.getUser().getUserNickname(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
