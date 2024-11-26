package com.fluffygram.newsfeed.domain.Image.controller;

import com.fluffygram.newsfeed.domain.Image.dto.ImageRequestDto;
import com.fluffygram.newsfeed.domain.Image.dto.ImageResponseDto;
import com.fluffygram.newsfeed.domain.Image.entity.Image;
import com.fluffygram.newsfeed.domain.Image.service.ImageService;
import com.fluffygram.newsfeed.domain.base.enums.ImageStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image_files")
@RequiredArgsConstructor
public class UserImageController {

    private final ImageService imageService;

    /**
     *  이미지 저장 API
     *
     * @return ResponseEntity<ImageResponseDto>  이미지 정보 및 http 상태 전달
     *
     */
    @PostMapping
    public ResponseEntity<ImageResponseDto> saveImage(@RequestParam MultipartFile file, @Valid @RequestBody ImageRequestDto requestDto) {
        Image image = imageService.saveImage(file, requestDto.getStatusId(), ImageStatus.valueOf(requestDto.getImageStatus()));

        ImageResponseDto imageResponseDto = new ImageResponseDto(image.getId(), image.getDBFileName(), image.getCreatedAt(), image.getModifiedAt());

        return new ResponseEntity<>(imageResponseDto, HttpStatus.OK);
    }

    /**
     *  이미지 조회 API
     *
     * @return ResponseEntity<String>  base64로 인코딩된 이미지 및 http 상태 전달
     *
     */
    @GetMapping
    public ResponseEntity<String> getImage(@RequestParam Long statusId, @RequestParam String imageStatus) {
        String base64Image = imageService.getImage(statusId, ImageStatus.valueOf(imageStatus));

        return new ResponseEntity<>(base64Image, HttpStatus.OK);
    }

    /**
     *  이미지 수정 API
     *
     * @return ResponseEntity<ImageResponseDto>  이미지 정보 및 http 상태 전달
     *
     */
    @PatchMapping
    public ResponseEntity<ImageResponseDto> UpdateImage(@RequestParam MultipartFile file, @Valid @RequestBody ImageRequestDto requestDto) {
        Image image = imageService.updateImage(file, requestDto.getStatusId(), ImageStatus.valueOf(requestDto.getImageStatus()));

        ImageResponseDto imageResponseDto = new ImageResponseDto(image.getId(), image.getDBFileName(), image.getCreatedAt(), image.getModifiedAt());

        return new ResponseEntity<>(imageResponseDto, HttpStatus.OK);
    }

    /**
     *  이미지 삭제 API
     *
     * @return ResponseEntity<Void>  http 상태 전달
     *
     */
    @DeleteMapping("{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId, ImageStatus.ORPHANAGE);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
