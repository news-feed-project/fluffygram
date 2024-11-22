package com.fluffygram.newsfeed.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


//게시글 생성 요청
@Getter
public class CreateBoardRequestDto {

    @NotBlank
    private final Long userId;//사용자 id

    @NotBlank
    private final String title;//게시물 제목


    private final String contents;//게시물 내용

    public CreateBoardRequestDto(Long userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }
}
