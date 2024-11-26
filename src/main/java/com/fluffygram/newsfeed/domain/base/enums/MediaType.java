package com.fluffygram.newsfeed.domain.base.enums;

import lombok.Getter;

@Getter
public enum MediaType {
    JPEG("image/jpeg"),PNG("image/png"),GIF("image/gif"),BMP("image/bmp");

    final String url;


    MediaType(String url) {
        this.url = url;
    }
}
