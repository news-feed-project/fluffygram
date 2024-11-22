package com.fluffygram.newsfeed.domain.board.dto;


import com.fluffygram.newsfeed.domain.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

//전체 게시물 조회
@Getter
public class BoardListResponseDto {
    private final Long id;//게시물 id
    private final String title;//게시물 제목
    private final String userNickname;//유저 닉네임
    private final LocalDateTime createdAt;//게시물 생성일
    private final LocalDateTime modifiedAt;//게시물 수정일

    public BoardListResponseDto(Long id, String title, String userNickname, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.userNickname = userNickname;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    //게시물 전체 조회
    public static BoardListResponseDto toDto(Board board) {
        return new BoardListResponseDto(
                board.getId(),
                board.getTitle(),
                board.getUser().getUserNickname(),
                board.getCreatedAt(),
                board.getModifiedAt());
    }
}

