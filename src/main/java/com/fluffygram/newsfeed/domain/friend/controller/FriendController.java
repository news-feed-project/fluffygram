package com.fluffygram.newsfeed.domain.friend.controller;

import com.fluffygram.newsfeed.domain.friend.dto.FriendAcceptRequestDto;
import com.fluffygram.newsfeed.domain.friend.dto.FriendRequestDto;
import com.fluffygram.newsfeed.domain.friend.dto.FriendResponseDto;
import com.fluffygram.newsfeed.domain.friend.service.FriendService;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.global.config.Const;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    /**
     * 친구 요청 보내기 API
     *
     * @param session    현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param requestDto 요청 Dto
     * @return HTTP 상태 코드 반환
     */
    @PostMapping
    public ResponseEntity<Void> sendFriendRequest(
            HttpSession session,
            @RequestBody @Valid FriendRequestDto requestDto) {

        User user = (User) session.getAttribute(Const.LOGIN_USER);

        Long loginUserId = user.getId();

        friendService.sendFriendRequest(loginUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 친구 요청 수락 API
     *
     * @param session    현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param requestDto 요청 Dto
     * @return HTTP 상태 코드 반환
     */
    @PutMapping("/accept")
    public ResponseEntity<Void> acceptFriendRequest(
            HttpSession session,
            @RequestBody @Valid FriendAcceptRequestDto requestDto) {

        User user = (User) session.getAttribute(Const.LOGIN_USER);

        Long loginUserId = user.getId();

        friendService.acceptFriendRequest(loginUserId, requestDto.getSendUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 친구 거절 및 삭제 API
     * 친구 거절과 삭제는 같은 기능을 함.
     *
     * @param session    현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param requestDto 요청 Dto
     * @return HTTP 상태 코드 반환
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(
            HttpSession session,
            @RequestBody @Valid FriendRequestDto requestDto) {

        User user = (User) session.getAttribute(Const.LOGIN_USER);

        Long loginUserId = user.getId();

        friendService.deleteFriend(loginUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 전체친구조회 API
     *
     * @param session    현재 세션에서 로그인한 사용자의 ID 가져옴
     *
     * @return 친구의 Id를 List로 반환
     */
    @GetMapping
    public ResponseEntity<List<FriendResponseDto>> findAllFriends(
            HttpSession session) {

        User user = (User) session.getAttribute(Const.LOGIN_USER);

        Long loginUserId = user.getId();

        List<FriendResponseDto> result = friendService.findAllFriends(loginUserId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}



