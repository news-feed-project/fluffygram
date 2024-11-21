package user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.dto.CommentsResponseDto;
import user.dto.CommentsWithUsernameResponseDto;
import user.dto.CreateCommentsRequestDto;
import user.dto.UpdateCommentsRequestDto;
import user.service.CommentsService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;

    //댓글 생성(작성)
    @PostMapping
    public ResponseEntity<CommentsResponseDto> save (@RequestBody CreateCommentsRequestDto requestDto){

        CommentsResponseDto friendResponseDto =
                commentsService.save(
                        requestDto.getboardId(),
                        requestDto.getuserId(),
                        requestDto.getcomment()
                );
        return new ResponseEntity<>(friendResponseDto, HttpStatus.CREATED);
    } //저장

    //댓글 전체 조회
    @GetMapping
    public ResponseEntity<List<CommentsResponseDto>> findAllComments(){
        List<CommentsResponseDto> commentsResponseDtoList = commentsService.findAllComments();
        return new ResponseEntity<>(commentsResponseDtoList, HttpStatus.OK);
    }

    //댓글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentsWithUsernameResponseDto> findCommentsById(@PathVariable Long id) {
        CommentsWithUsernameResponseDto commentWithUsernameResponseDto = commentsService.findCommentsById(id);

        return new ResponseEntity<>(commentWithUsernameResponseDto, HttpStatus.OK);

    }

    //댓글 단건 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentsResponseDto> updateComments(
        @PathVariable Long id,
        @RequestBody UpdateCommentsRequestDto requestDto
    ) {
        commentsService.updateComments(requestDto.getcomments());

        return new ResponseEntity<>(HttpStatus.OK);
    }




}
