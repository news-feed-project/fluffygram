package com.fluffygram.newsfeed.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

//게시물 수정 요청
@Getter
public class UpdateBoardRequestDto {


    private final String title;//게시물 제목

    private final String contents;//게시물 내용

    public UpdateBoardRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
