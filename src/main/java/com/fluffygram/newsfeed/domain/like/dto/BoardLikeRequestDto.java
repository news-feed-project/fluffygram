package com.fluffygram.newsfeed.domain.like.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BoardLikeRequestDto {

    @NotNull
    private Long userId; //사용자 id

    public BoardLikeRequestDto(Long userId) {
        this.userId = userId;
    }

    public BoardLikeRequestDto() {
    }

}
