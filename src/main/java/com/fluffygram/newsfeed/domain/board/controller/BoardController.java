package com.fluffygram.newsfeed.domain.board.controller;

import com.fluffygram.newsfeed.domain.board.dto.BoardResponseDto;
import com.fluffygram.newsfeed.domain.board.dto.CreateBoardRequestDto;
import com.fluffygram.newsfeed.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private  final BoardService boardService;

    //게시물 생성(저장)
    @PostMapping
    public ResponseEntity<BoardResponseDto> save(@RequestBody CreateBoardRequestDto requestDto){
        BoardResponseDto boardResponseDto =
                boardService.save(
                        requestDto.getUserId(),
                        requestDto.getTitle(),
                        requestDto.getContents()
                );

        return  new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
    }

    //게시물 전체 List 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> findAllBoard(){
        List<BoardResponseDto> boardResponseDtoList =
                boardService.findAllBoard();

        return  new ResponseEntity<>(boardResponseDtoList, HttpStatus.OK);
    }



}
