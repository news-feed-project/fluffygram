package com.fluffygram.newsfeed.domain.like.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardLikeRequestDto {

    @NotBlank
    private final Long userId; //사용자 id

    @NotBlank
    private final Long boardId; //게시물 id

    //좋아요를 한 사용자id와 게시글 id 전달받기
    public BoardLikeRequestDto(Long userId, Long boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }
}
