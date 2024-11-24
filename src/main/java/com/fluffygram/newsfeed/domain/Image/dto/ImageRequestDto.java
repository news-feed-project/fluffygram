package com.fluffygram.newsfeed.domain.Image.dto;

import lombok.Getter;

@Getter
public class ImageRequestDto {

    private final Long statusId;

    private final String imageStatus;

    public ImageRequestDto(Long statusId, String imageStatus) {
        this.statusId = statusId;
        this.imageStatus = imageStatus;
    }
}
