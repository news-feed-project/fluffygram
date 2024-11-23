package com.fluffygram.newsfeed.global.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Const {
    public static final String LOGIN_USER = "loginUser";

    public static String USER_IMAGE_STORAGE;

    public static String BOARD_IMAGE_STORAGE;


    static {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")){
            USER_IMAGE_STORAGE = "C:/fluffygram/user/image";
            BOARD_IMAGE_STORAGE = "C:/fluffygram/board/image";
        }else{
            USER_IMAGE_STORAGE = "/fluffygram/user/image";
            BOARD_IMAGE_STORAGE = "/fluffygram/board/image";
        }
    }

    public static final Path USER_IMAGE_STORAGE_PATH = Paths.get(USER_IMAGE_STORAGE);
    public static final Path BOARD_IMAGE_STORAGE_PATH = Paths.get(BOARD_IMAGE_STORAGE);
}