package com.fluffygram.newsfeed.domain.like.controller;

import com.fluffygram.newsfeed.domain.like.dto.BoardLikeRequestDto;
import com.fluffygram.newsfeed.domain.like.dto.BoardLikeResponseDto;
import com.fluffygram.newsfeed.domain.like.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards/{boardId}/boardlikes")
@RequiredArgsConstructor
public class BoardLikeController {
    private  final BoardLikeService boardLikeService;


}
