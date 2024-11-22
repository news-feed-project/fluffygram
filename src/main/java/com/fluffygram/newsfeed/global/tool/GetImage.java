package com.fluffygram.newsfeed.global.tool;

import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;


public class GetImage {

    public static String getImage(String path, String imageUrl) {

        try {
            // 1. 로컬에 저장된 이미지 파일 경로 설정 및 파일 불러오기
            File imgFile = new File(path + "/" + imageUrl);

            if (!imgFile.exists()) {
                throw new BusinessException(ExceptionType.FILE_NOT_FOUND);
            }

            // 2. 파일을 Base64로 변환
            byte[] fileContent = Files.readAllBytes(imgFile.toPath());
            String base64Image = Base64.getEncoder().encodeToString(fileContent);

            return "data:image/jpeg;base64," + base64Image;
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
