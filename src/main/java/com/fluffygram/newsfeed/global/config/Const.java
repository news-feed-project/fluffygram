package com.fluffygram.newsfeed.global.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Const {
    public static final String LOGIN_USER = "loginUser";

    public static final String IMAGE_STORAGE = "C:/fluffygram/user/image";

    public static final Path IMAGE_STORAGE_PATH = Paths.get(IMAGE_STORAGE);
}