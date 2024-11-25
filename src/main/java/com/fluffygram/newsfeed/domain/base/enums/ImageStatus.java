package com.fluffygram.newsfeed.domain.base.enums;

import com.fluffygram.newsfeed.global.config.Const;
import lombok.Getter;

import java.nio.file.Path;

@Getter
public enum ImageStatus {
    USER(Const.USER_IMAGE_STORAGE_PATH, Const.USER_IMAGE_STORAGE),
    BOARD(Const.BOARD_IMAGE_STORAGE_PATH, Const.BOARD_IMAGE_STORAGE),
    ORPHANAGE(null, null);

    final Path path;
    final String pathUrl;

    ImageStatus(Path path, String pathUrl) {
        this.path = path;
        this.pathUrl = pathUrl;
    }
}
