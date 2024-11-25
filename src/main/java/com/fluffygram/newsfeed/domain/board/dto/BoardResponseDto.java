package com.fluffygram.newsfeed.domain.board.dto;


import com.fluffygram.newsfeed.domain.Image.entity.Image;
import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.global.config.Const;
import com.fluffygram.newsfeed.global.tool.GetImage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//게시물 단일 조회
@Getter
public class BoardResponseDto {

    private final Long id;//게시물 id
    private final String title;//게시물 제목
    private String contents;//게시물 내용
    private final String userNickname;//유저 닉네임

    private final List<String> boardBase64Images;

    private final LocalDateTime createdAt;//게시물 생성일
    private final LocalDateTime modifiedAt;//게시물 수정일

    public BoardResponseDto(Long id, String title, String userNickname, LocalDateTime createdAt, LocalDateTime modifiedAt, List<String> boardBase64Images) {
        this.id = id;
        this.title = title;
        this.userNickname = userNickname;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.boardBase64Images = boardBase64Images;
    }

    public BoardResponseDto(Long id, String title, String contents, String userNickname, LocalDateTime createdAt, LocalDateTime modifiedAt, List<String> boardBase64Images) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userNickname = userNickname;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.boardBase64Images = boardBase64Images;
    }


    public static BoardResponseDto toDto(Board board, List<Image> images) {
        List<String> base64Images = new ArrayList<>();
        for (Image image : images) {
            base64Images.add(GetImage.getImage(Const.BOARD_IMAGE_STORAGE, image.getDBFileName()));
        }

        return new BoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                board.getUser().getUserNickname(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                base64Images);
    }


    public static BoardResponseDto toDtoForAll(Board board, List<Image> images) {
        List<String> base64Images = new ArrayList<>();
        for (Image image : images) {
            base64Images.add(GetImage.getImage(Const.BOARD_IMAGE_STORAGE, image.getDBFileName()));
        }

        return new BoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getUser().getUserNickname(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                base64Images);
    }
}
