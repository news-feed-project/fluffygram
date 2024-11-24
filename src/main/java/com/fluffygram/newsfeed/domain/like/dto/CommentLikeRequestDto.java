package com.fluffygram.newsfeed.domain.like.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentLikeRequestDto {

    @NotBlank
    private Long userId; //사용자 id

    public CommentLikeRequestDto(Long userId) {
        this.userId = userId;
    }

    public CommentLikeRequestDto() {
    }

}
