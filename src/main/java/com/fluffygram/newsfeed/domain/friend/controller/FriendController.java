package com.fluffygram.newsfeed.domain.friend.controller;

import com.fluffygram.newsfeed.domain.friend.dto.FriendRequestDto;
import com.fluffygram.newsfeed.domain.friend.service.FriendService;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    /**
     * 친구 요청 API
     *
     * @param session       현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param requestDto    요청 Dto
     * @return              HTTP 상태 코드 반환
     *
     */
    @PostMapping
    public ResponseEntity<Void> sendFriendRequest(
            HttpSession session,
            @RequestBody FriendRequestDto requestDto) {

        Long loginUserId = (Long) session.getAttribute("userId");

        if (loginUserId == null) {
            throw new BusinessException(ExceptionType.USER_NOT_FOUND);
        }
        if (!loginUserId.equals(requestDto.getSendUserId()) || loginUserId.equals(requestDto.getReceivedUserId())) {
            throw new BusinessException(ExceptionType.USER_NOT_MATCH);
        }

        friendService.sendFriendRequest(loginUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 친구 요청 수락 API
     *
     * @param session        현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param requestDto     요청 Dto
     * @return               HTTP 상태 코드 반환
     *
     */
    @GetMapping("/accept")
    public ResponseEntity<Void> acceptFriendRequest(
            HttpSession session,
            @RequestBody FriendRequestDto requestDto) {

        Long loginUserId = (Long) session.getAttribute("userId");

        if (loginUserId == null) {
            throw new BusinessException(ExceptionType.USER_NOT_FOUND);
        }
        if (!loginUserId.equals(requestDto.getSendUserId()) || loginUserId.equals(requestDto.getReceivedUserId())) {
            throw new BusinessException(ExceptionType.USER_NOT_MATCH);
        }

        friendService.acceptFriendRequest(loginUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 친구 요청 거절 API
     *
     * @param session        현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param requestDto     요청 Dto
     * @return               HTTP 상태 코드 반환
     *
     */
    @GetMapping("/reject")
    public ResponseEntity<Void> rejectFriendRequest(
            HttpSession session,
            @RequestBody FriendRequestDto requestDto) {

        Long loginUserId = (Long) session.getAttribute("userId");

        if (loginUserId == null) {
            throw new BusinessException(ExceptionType.USER_NOT_FOUND);
        }
        if (!loginUserId.equals(requestDto.getSendUserId()) || loginUserId.equals(requestDto.getReceivedUserId())) {
            throw new BusinessException(ExceptionType.USER_NOT_MATCH);
        }

        friendService.rejectFriendRequest(loginUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 친구 삭제 API
     *
     * @param session        현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param requestDto     요청 Dto
     * @return               HTTP 상태 코드 반환
     *
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(
            HttpSession session,
            @RequestBody FriendRequestDto requestDto) {

        Long loginUserId = (Long) session.getAttribute("userId");

        if (loginUserId == null) {
            throw new BusinessException(ExceptionType.USER_NOT_FOUND);
        }
        if (!loginUserId.equals(requestDto.getSendUserId()) || loginUserId.equals(requestDto.getReceivedUserId())) {
            throw new BusinessException(ExceptionType.USER_NOT_MATCH);
        }

        friendService.deleteFriend(loginUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO : 모든 친구 조회 API 만들기. List타입 반환 유저가 있어야할듯?
    @GetMapping("/{userId}")
    public ResponseEntity<Void> findAllFriends(@PathVariable Long userId) {

        return null;
    }
}



