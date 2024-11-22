package com.fluffygram.newsfeed.domain.like.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardLikeResponseDto {
    private final Long id; //좋아요 id
//    private final Integer count;//좋아요 개수 카운트
    private final LocalDateTime createdAt;//좋아요 생성일
    private final LocalDateTime modifiedAt;//좋아요 수정일
    private final Integer likeStatus;//좋아요 상태

    public BoardLikeResponseDto(Long id, LocalDateTime createdAt, LocalDateTime modifiedAt, Integer likeStatus) {
        this.id = id;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.likeStatus = likeStatus;
    }
}
