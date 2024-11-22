package com.fluffygram.newsfeed.domain.Image.controller;

import com.fluffygram.newsfeed.domain.Image.dto.ImageResponseDto;
import com.fluffygram.newsfeed.domain.Image.entity.Image;
import com.fluffygram.newsfeed.domain.Image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/imagefiles")
@RequiredArgsConstructor
public class UserImageController {

    private final ImageService imageService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<ImageResponseDto> saveImage(@RequestParam MultipartFile file, @PathVariable Long userId) {
        Image image = imageService.saveImage(file, userId);

        ImageResponseDto imageResponseDto = new ImageResponseDto(image.getId(), image.getDBFileName(), image.getCreatedAt(), image.getModifiedAt());

        return new ResponseEntity<>(imageResponseDto, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<String> getUserImage(@RequestParam Long userId) {
        String base64Image = imageService.getImage(userId);

        // HTTP 응답 생성 (Content-Disposition: inline)
        return ResponseEntity.ok().body(base64Image);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<ImageResponseDto> UpdateImage(@RequestParam MultipartFile file, @PathVariable Long userId) {
        Image image = imageService.updateImage(file, userId);

        ImageResponseDto imageResponseDto = new ImageResponseDto(image.getId(), image.getDBFileName(), image.getCreatedAt(), image.getModifiedAt());

        return new ResponseEntity<>(imageResponseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{imageName}")
    public ResponseEntity<Void> deleteUserImage(@PathVariable String imageName) {
        imageService.deleteImage(imageName);


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