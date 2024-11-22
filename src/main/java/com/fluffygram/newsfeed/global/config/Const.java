package com.fluffygram.newsfeed.global.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Const {
    public static final String LOGIN_USER = "loginUser";

    public static final String USER_IMAGE_STORAGE = "C:/fluffygram/user/image";

    public static final Path USER_IMAGE_STORAGE_PATH = Paths.get(USER_IMAGE_STORAGE);

    public static final String BOARD_IMAGE_STORAGE = "C:/fluffygram/board/image";

    public static final Path BOARD_IMAGE_STORAGE_PATH = Paths.get(BOARD_IMAGE_STORAGE);
}