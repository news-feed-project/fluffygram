package com.fluffygram.newsfeed.domain.user.image;

import com.fluffygram.newsfeed.global.config.Const;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.net.MalformedURLException;
import java.nio.file.Path;


public class GetUserImage {

    public static Resource getImage(String imageUrl) {
        Resource resource;

        try {
            // 파일 경로 생성
            Path filePath = Const.IMAGE_STORAGE_PATH.resolve(imageUrl).normalize();

            // 파일을 Resource 객체로 변환
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("파일이 없습니다.");
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return resource;
    }

}
