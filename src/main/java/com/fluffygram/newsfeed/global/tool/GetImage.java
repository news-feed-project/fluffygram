package com.fluffygram.newsfeed.global.tool;

import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.net.MalformedURLException;
import java.nio.file.Path;


public class GetImage {

    public static Resource getImage(Path path, String imageUrl) {
        Resource resource;

        try {
            // 파일 경로 생성
            Path filePath = path.resolve(imageUrl).normalize();

            // 파일을 Resource 객체로 변환
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new BusinessException(ExceptionType.FILE_NOT_FOUND);
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return resource;
    }
}
