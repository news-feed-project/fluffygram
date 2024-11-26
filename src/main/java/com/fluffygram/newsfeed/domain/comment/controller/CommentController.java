package com.fluffygram.newsfeed.domain.comment.controller;

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
import com.fluffygram.newsfeed.domain.comment.dto.CommentResponseDto;
import com.fluffygram.newsfeed.domain.comment.dto.CreateCommentRequestDto;
import com.fluffygram.newsfeed.domain.comment.dto.UpdateCommentRequestDto;
import com.fluffygram.newsfeed.domain.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 생성(작성)
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @Valid @RequestBody CreateCommentRequestDto requestDto,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        CommentResponseDto commentResponseDto = commentService.createComment(
                requestDto.getBoardId(), requestDto.getUserId(),
                requestDto.getComment(), user.getId()
        );
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }
    //댓글 전체 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAllComment(
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam Long boardId) {
        List<CommentResponseDto> commentResponseDtoList =
                commentService.findAllCommentByBoardId(pageable, boardId);
        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    //댓글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findCommentsById(@PathVariable Long id) {
        CommentResponseDto commentResponseDto = commentService.findCommentById(id);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);

    }

    //댓글 단건 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComments(
        @PathVariable Long id,
        @Valid @RequestBody UpdateCommentRequestDto requestDto,
        HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        CommentResponseDto commentResponseDto = commentService.UpdateComments(id, requestDto.getComment(), user.getId());

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);

    }
    //댓글 단건 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<CommentResponseDto> deleteComments(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        commentService.deleteComment(id, user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
