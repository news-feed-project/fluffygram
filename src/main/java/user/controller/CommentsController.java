package user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.dto.CommentsResponseDto;
import user.dto.CommentsWithUsernameResponseDto;
import user.dto.CreateCommentsRequestDto;
import user.dto.UpdateCommentsRequestDto;
import user.service.CommentService;
import user.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    //댓글 생성(작성)
    @PostMapping
    public ResponseEntity<CommentsResponseDto> createComment(
            @RequestBody CreateCommentsRequestDto requestDto) {
        CommentsResponseDto commentsResponseDto = commentService.createComment(
                requestDto.getBoardId(), requestDto.getUserId(), requestDto.getComment()
        );
        return new ResponseEntity<>(commentsResponseDto, HttpStatus.CREATED);
    }

    //댓글 전체 조회
    @GetMapping
    public ResponseEntity<List<CommentsResponseDto>> findAllComments(){
        List<CommentsResponseDto> commentsResponseDtoList = commentService.findAllComments();
        return new ResponseEntity<>(commentsResponseDtoList, HttpStatus.OK);
    }

    //댓글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentsWithUsernameResponseDto> findCommentsById(@PathVariable Long id) {
        CommentsWithUsernameResponseDto commentWithUsernameResponseDto = commentService.findCommentsById(id);

        return new ResponseEntity<>(commentWithUsernameResponseDto, HttpStatus.OK);

    }

    //댓글 단건 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentsResponseDto> updateComments(
        @PathVariable Long id,
        @RequestBody UpdateCommentsRequestDto requestDto
    ) {
        commentService.UpdateComments(requestDto.getComment());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //댓글 단건 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<CommentsResponseDto> deleteComments(@PathVariable Long id) {
        commentService.deleteComment(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }




}
