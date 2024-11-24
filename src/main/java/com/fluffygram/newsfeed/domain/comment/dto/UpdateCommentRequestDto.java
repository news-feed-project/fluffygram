package com.fluffygram.newsfeed.domain.comment.dto;

import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {
    private final String comment;

    public UpdateCommentRequestDto(String comment) {
    this.comment = comment;
    }

}