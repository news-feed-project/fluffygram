package com.fluffygram.newsfeed.domain.Image.controller;

import com.fluffygram.newsfeed.domain.Image.dto.ImageResponseDto;
import com.fluffygram.newsfeed.domain.Image.entity.BoardImage;
import com.fluffygram.newsfeed.domain.Image.service.BoardImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/boardimagefiles")
@RequiredArgsConstructor
public class BoardImageController {

    private final BoardImageServiceImpl boardImageServiceImpl;

    @PostMapping("/boards/{boardId}")
    public ResponseEntity<ImageResponseDto> saveImage(@RequestParam MultipartFile file, @PathVariable Long boardId) {
        BoardImage boardImage = boardImageServiceImpl.saveImage(file, boardId);

        ImageResponseDto imageResponseDto = new ImageResponseDto(boardImage.getId(), boardImage.getDBFileName(), boardImage.getCreatedAt(), boardImage.getModifiedAt());

        return new ResponseEntity<>(imageResponseDto, HttpStatus.OK);
    }

    @PostMapping("/boards/{boardId}/list")
    public ResponseEntity<List<ImageResponseDto>> saveImages(@RequestParam List<MultipartFile> files, @PathVariable Long boardId) {
        return new ResponseEntity<>(boardImageServiceImpl.saveImages(files, boardId), HttpStatus.OK);
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getBoardImage(@PathVariable String imageName) {
        Resource resource = boardImageServiceImpl.getImage(imageName);

        // HTTP 응답 생성 (Content-Disposition: inline)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg") // 기본 MIME 타입
                .body(resource);
    }




    @DeleteMapping("/{imageName}")
    public ResponseEntity<Void> deleteBoardImage(@PathVariable String imageName) {
        boardImageServiceImpl.deleteImage(imageName);


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
