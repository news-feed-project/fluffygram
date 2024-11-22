package com.fluffygram.newsfeed.domain.Image.controller;

import com.fluffygram.newsfeed.domain.Image.dto.ImageResponseDto;
import com.fluffygram.newsfeed.domain.Image.entity.UserImage;
import com.fluffygram.newsfeed.domain.Image.service.UserImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/userimagefiles")
@RequiredArgsConstructor
public class UserImageController {

    private final UserImageServiceImpl userImageServiceImpl;

    @PostMapping("/users/{userId}")
    public ResponseEntity<ImageResponseDto> saveImage(@RequestParam MultipartFile file, @PathVariable Long userId) {
        UserImage userImage = userImageServiceImpl.saveImage(file, userId);

        ImageResponseDto imageResponseDto = new ImageResponseDto(userImage.getId(), userImage.getDBFileName(), userImage.getCreatedAt(), userImage.getModifiedAt());

        return new ResponseEntity<>(imageResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getUserImage(@PathVariable String imageName) {
        Resource resource = userImageServiceImpl.getImage(imageName);

        // HTTP 응답 생성 (Content-Disposition: inline)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg") // 기본 MIME 타입
                .body(resource);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<ImageResponseDto> UpdateImage(@RequestParam MultipartFile file, @PathVariable Long userId) {
        UserImage userImage = userImageServiceImpl.updateImage(file, userId);

        ImageResponseDto imageResponseDto = new ImageResponseDto(userImage.getId(), userImage.getDBFileName(), userImage.getCreatedAt(), userImage.getModifiedAt());

        return new ResponseEntity<>(imageResponseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{imageName}")
    public ResponseEntity<Void> deleteUserImage(@PathVariable String imageName) {
        userImageServiceImpl.deleteImage(imageName);


        return new ResponseEntity<>(HttpStatus.OK);
    }
}

    /*
    * 클라이언트 예시
    *
    const imageUrl = "http://localhost:8080/images/example.png";
    const imgElement = document.getElementById("myImage");

    fetch(imageUrl)
      .then(response => {
        if (response.ok) {
        * blob 을 이용
          return response.blob();
        } else {
          throw new Error("Image not found");
        }
      })
      .then(blob => {
        const imageUrl = URL.createObjectURL(blob);
        imgElement.src = imageUrl;
      })
      .catch(error => console.error("Error loading image:", error));

     */