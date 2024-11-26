package com.fluffygram.newsfeed.domain.like.dto;


import com.fluffygram.newsfeed.domain.like.entity.BoardLike;
import com.fluffygram.newsfeed.domain.base.enums.LikeStatus;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class BoardLikeResponseDto {
    private final Long id; //좋아요 id
    private final Long userId;//사용자 id
    private final Long boardId;//게시물 id
    private final LocalDateTime createdAt;//좋아요 생성일
    private final LocalDateTime modifiedAt;//좋아요 수정일
    private final LikeStatus likeStatus;//좋아요 상태
    private final Long count;//좋아요 개수 카운트

    public BoardLikeResponseDto(Long id, Long userId, Long boardId, LocalDateTime createdAt, LocalDateTime modifiedAt, LikeStatus likeStatus, Long count) {
        this.id = id;
        this.userId = userId;
        this.boardId = boardId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.likeStatus = likeStatus;
        this.count = count;
    }

    public static BoardLikeResponseDto toDto(BoardLike boardLike, Long count) {
        return new BoardLikeResponseDto(
                boardLike.getId(),
                boardLike.getUser().getId(),
                boardLike.getBoard().getId(),
                boardLike.getCreatedAt(),
                boardLike.getModifiedAt(),
                boardLike.getLikeStatus(),
                count
                );
    }
}
