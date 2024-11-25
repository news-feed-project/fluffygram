package com.fluffygram.newsfeed.global.tool;

import com.fluffygram.newsfeed.domain.base.enums.MediaType;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.WrongAccessException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;


public class UploadImage {

    public static void FileAndDataUploadController(Path path) throws IOException {
        // 업로드 디렉토리 생성 (없으면 생성)
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public static String uploadUserImage(Path path, MultipartFile file) throws WrongAccessException {
        String uniqueFilename;

        if (file.isEmpty()) {
            return null;
        }

        String contentType = file.getContentType();
        String originalFileExtension;

        // 타입에 따른 확장자 결정
        if (ObjectUtils.isEmpty(contentType)) {
            // 타입 없으면 null
            throw new WrongAccessException(ExceptionType.FAIL_FILE_UPLOADED);
        } else {
            if (contentType.contains(MediaType.JPEG.getUrl())) {
                originalFileExtension = ".jpg";
            } else if (contentType.contains(MediaType.PNG.getUrl())) {
                originalFileExtension = ".png";
            } else {
                throw new WrongAccessException(ExceptionType.FAIL_FILE_UPLOADED);
            }
        }

        try {
            FileAndDataUploadController(path);

            // 고유 파일 이름 생성
            uniqueFilename = UUID.randomUUID() + originalFileExtension;

            // 파일 저장 경로
            Path filePath = path.resolve(uniqueFilename);

            // 파일 저장
            file.transferTo(filePath.toFile());

        } catch (IOException e) {
            throw new WrongAccessException(ExceptionType.FAIL_FILE_UPLOADED);

        }
        return uniqueFilename;
    }
}
