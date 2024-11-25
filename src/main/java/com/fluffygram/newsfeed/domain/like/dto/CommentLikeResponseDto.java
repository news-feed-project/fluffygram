package com.fluffygram.newsfeed.domain.like.dto;


import com.fluffygram.newsfeed.domain.like.entity.CommentLike;
import com.fluffygram.newsfeed.domain.base.enums.LikeStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentLikeResponseDto {
    private final Long id; //좋아요 id
    private final Long userId;//사용자 id
    private final Long commentId;//댓글 id
    private final LocalDateTime createdAt;//좋아요 생성일
    private final LocalDateTime modifiedAt;//좋아요 수정일
    private final LikeStatus likeStatus;//좋아요 상태
    private final Long count;//좋아요 개수 카운트

    public CommentLikeResponseDto(Long id, Long userId, Long commentId, LocalDateTime createdAt, LocalDateTime modifiedAt, LikeStatus likeStatus, Long count) {
        this.id = id;
        this.userId = userId;
        this.commentId = commentId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.likeStatus = likeStatus;
        this.count = count;
    }

    public static CommentLikeResponseDto toDto(CommentLike commentLike, Long count) {
        return new CommentLikeResponseDto(
                commentLike.getId(),
                commentLike.getUser().getId(),
                commentLike.getComment().getId(),
                commentLike.getCreatedAt(),
                commentLike.getModifiedAt(),
                commentLike.getLikeStatus(),
                count
                );
    }
}
