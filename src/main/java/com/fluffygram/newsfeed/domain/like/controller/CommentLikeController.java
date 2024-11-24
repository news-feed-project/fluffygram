package com.fluffygram.newsfeed.domain.like.controller;

import com.fluffygram.newsfeed.domain.like.dto.CommentLikeRequestDto;
import com.fluffygram.newsfeed.domain.like.dto.CommentLikeResponseDto;
import com.fluffygram.newsfeed.domain.like.service.CommentLikeService;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.global.config.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/comments/{commentId}/commentlikes")
@RequiredArgsConstructor
public class CommentLikeController {
    private  final CommentLikeService commentLikeService;

    //게시글 좋아요 토글(활성화, 비활성화)
    @PostMapping
    public ResponseEntity<CommentLikeResponseDto> toggleLike(@PathVariable Long commentId, @RequestBody CommentLikeRequestDto requestDto, HttpServletRequest request){
        //로그인 된 id 가져오기
        //session이 있으면 가져오고 없으면 null return
        HttpSession session = request.getSession(false);
        //Session에서 "LOGIN_USER"를 Key로 가진 Value를 가져온다.
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        //요청 데이터 유효성 검증 url 댓글 id가 body에서 요청한 값이랑 다를 경우
        if (!commentId.equals(requestDto.getCommentId())) {
            throw new IllegalArgumentException("commentId가 일치하지 않습니다.");
        }

        CommentLikeResponseDto commentLikeResponseDto =
                commentLikeService.toggleLike(
                        user.getId(), //유저 확인
                        requestDto.getUserId(),
                        requestDto.getCommentId()
                );

        return  new ResponseEntity<>(commentLikeResponseDto, HttpStatus.CREATED);
    }
    



}
