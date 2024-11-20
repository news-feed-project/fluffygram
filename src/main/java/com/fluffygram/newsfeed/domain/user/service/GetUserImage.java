package com.fluffygram.newsfeed.domain.user.service;

import com.fluffygram.newsfeed.global.config.Const;
import org.springframework.util.FileCopyUtils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;


public class GetUserImage {

    public static String getUserImage(String filename) {
        String encodedImage;
        try {
            // 이미지 파일 로드
            File file = new File(Const.IMAGE_STORAGE_PATH + "/" + filename);
            if (!file.exists()) {
                throw new RuntimeException("파일이 존재하지 않습니다.");
            }

            // 파일을 Base64로 인코딩
            byte[] fileContent = FileCopyUtils.copyToByteArray(file);
            encodedImage = Base64.getEncoder().encodeToString(fileContent);


        } catch (MalformedURLException | FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return encodedImage;
    }

}
