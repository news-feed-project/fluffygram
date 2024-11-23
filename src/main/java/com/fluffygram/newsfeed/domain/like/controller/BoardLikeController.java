package com.fluffygram.newsfeed.domain.like.controller;

import com.fluffygram.newsfeed.domain.like.dto.BoardLikeRequestDto;
import com.fluffygram.newsfeed.domain.like.dto.BoardLikeResponseDto;
import com.fluffygram.newsfeed.domain.like.service.BoardLikeService;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.global.config.Const;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotMatchByUserIdException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

    //게시글 좋아요 활성화, 비활성화
    @PostMapping
    public ResponseEntity<BoardLikeResponseDto> createLike(@PathVariable Long boardId, @Valid @RequestBody BoardLikeRequestDto requestDto, HttpServletRequest request){
        //로그인 된 id 가져오기
        //session이 있으면 가져오고 없으면 null return
        HttpSession session = request.getSession(false);
        //Session에서 "LOGIN_USER"를 Key로 가진 Value를 가져온다.
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        //요청 데이터 유효성 검증 url 게시물 id가 body에서 요청한 값이랑 다를 경우
        if (!boardId.equals(requestDto.getBoardId())) {
            throw new NotMatchByUserIdException(ExceptionType.BOARD_NOT_FOUND);
        }

        BoardLikeResponseDto boardLikeResponseDto =
                boardLikeService.createLike(
                        user.getId(), //유저 확인
                        requestDto.getUserId(),
                        requestDto.getBoardId()
                );

        return  new ResponseEntity<>(boardLikeResponseDto, HttpStatus.CREATED);
    }
    



}
