package com.fluffygram.newsfeed.domain.board.controller;

import com.fluffygram.newsfeed.domain.board.dto.BoardResponseDto;
import com.fluffygram.newsfeed.domain.board.dto.CreateBoardRequestDto;
import com.fluffygram.newsfeed.domain.board.dto.UpdateBoardRequestDto;
import com.fluffygram.newsfeed.domain.board.service.BoardService;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.global.config.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private  final BoardService boardService;

    //게시물 생성(저장)
    @PostMapping
    public ResponseEntity<BoardResponseDto> save(@RequestParam(required = false) List<MultipartFile> boardImages,
                                                 @Valid @ModelAttribute CreateBoardRequestDto requestDto,
                                                 HttpServletRequest request){
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        BoardResponseDto boardResponseDto = boardService.save(requestDto.getUserId(),
                requestDto.getTitle(),
                requestDto.getContents(),
                boardImages, user.getId()
        );

        return  new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
    }

    //-- contents 제외한 dto 로 변경
    //게시물 전체 List 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> findAllBoard(@PageableDefault() Pageable pageable,
                                                                   @Valid @ModelAttribute() PaginationCondition paginationCondition){
        List<BoardResponseDto> boardResponseDtoList =
                boardService.findAllBoard(pageable);

        return  new ResponseEntity<>(boardResponseDtoList, HttpStatus.OK);
    }

   //게시물 ID로 특정 게시물 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> findBoardById(@PathVariable Long id){
        BoardResponseDto boardResponseDto = boardService.findBoardById(id);

        return  new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    //게시물 ID로 특정 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id,
                                                        @RequestParam(required = false) List<MultipartFile> boardImages,
                                                        @Valid @ModelAttribute UpdateBoardRequestDto requestDto,
                                                        HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        BoardResponseDto boardResponseDto = boardService.updateBoard(
                id,
                requestDto.getTitle(),
                requestDto.getContents(),
                user.getId(),
                boardImages
        );

        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }//updateSchedule

    //게시물 ID로 특정 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        boardService.deleteBoard(id, user.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }//delete
    
}
