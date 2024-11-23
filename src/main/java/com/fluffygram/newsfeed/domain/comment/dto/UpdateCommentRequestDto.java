package com.fluffygram.newsfeed.domain.comment.dto;

import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {
    private final long boardId;
    private final long userId;
    private final String comment;

    public UpdateCommentRequestDto(long boardId, long userId, String comment) {
    this.boardId = boardId;
    this.userId = userId;
    this.comment = comment;
    }

}