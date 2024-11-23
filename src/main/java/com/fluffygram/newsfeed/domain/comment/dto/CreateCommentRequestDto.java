package com.fluffygram.newsfeed.domain.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    @NotNull
    private final long boardId;

    @NotNull
    private final long userId;

    @NotNull
    private final String comment;


    public CreateCommentRequestDto(final long boardId, final long userId, final String comment) {
        this.boardId = boardId;
        this.userId = userId;
        this.comment = comment;
    }


}
