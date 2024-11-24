package com.fluffygram.newsfeed.domain.like.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentLikeRequestDto {

    @NotBlank
    private final Long userId; //사용자 id

    @NotBlank
    private final Long commentId; //게시물 id

    //좋아요를 한 사용자 id와 댓글 id 전달받기
    public CommentLikeRequestDto(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
