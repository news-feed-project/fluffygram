package com.fluffygram.newsfeed.domain.userImage.controller;

import com.fluffygram.newsfeed.domain.userImage.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-images")
@RequiredArgsConstructor
public class UserImageController {

    private final UserImageService userImageService;

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getUserImage(@PathVariable String imageName) {
        Resource resource = userImageService.getUserImage(imageName);

        // HTTP 응답 생성 (Content-Disposition: inline)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg") // 기본 MIME 타입
                .body(resource);
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
}
