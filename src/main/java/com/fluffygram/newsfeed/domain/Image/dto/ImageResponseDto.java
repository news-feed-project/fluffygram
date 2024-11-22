package com.fluffygram.newsfeed.domain.Image.dto;

import com.fluffygram.newsfeed.domain.Image.entity.BoardImage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ImageResponseDto {

    private final Long id;

    private final String filename;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public ImageResponseDto(Long id, String filename, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.filename = filename;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static ImageResponseDto BoardImageToDto(BoardImage image) {
        return new ImageResponseDto(image.getId(), image.getDBFileName(), image.getCreatedAt(), image.getModifiedAt());
    }

}
