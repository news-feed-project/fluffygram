package com.fluffygram.newsfeed.domain.comment.dto;

import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {
    private String comment;

    public UpdateCommentRequestDto(){

    }

    public UpdateCommentRequestDto(String comment) {

        this.comment = comment;
    }

}