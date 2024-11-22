package com.fluffygram.newsfeed.domain.friend.controller;

import com.fluffygram.newsfeed.domain.base.Valid.AccessWrongValid;
import com.fluffygram.newsfeed.domain.friend.dto.FriendRequestDto;
import com.fluffygram.newsfeed.domain.friend.dto.FriendResponseDto;
import com.fluffygram.newsfeed.domain.friend.service.FriendService;
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
    private final AccessWrongValid accessWrongValid;

    /**
     * 친구 요청 API
     *
     * @param session    현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param requestDto 요청 Dto
     * @return HTTP 상태 코드 반환
     */
    @PostMapping
    public ResponseEntity<Void> sendFriendRequest(
            HttpSession session,
            @RequestBody @Valid FriendRequestDto requestDto) {

        Long loginUserId = (Long) session.getAttribute("userId");

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
    @GetMapping("/accept")
    public ResponseEntity<Void> acceptFriendRequest(
            HttpSession session,
            @RequestBody @Valid FriendRequestDto requestDto) {

        Long loginUserId = (Long) session.getAttribute("userId");

        friendService.acceptFriendRequest(loginUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 친구 요청 거절 API
     *
     * @param session    현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param requestDto 요청 Dto
     * @return HTTP 상태 코드 반환
     */
    @GetMapping("/reject")
    public ResponseEntity<Void> rejectFriendRequest(
            HttpSession session,
            @RequestBody @Valid FriendRequestDto requestDto) {

        Long loginUserId = (Long) session.getAttribute("userId");

        friendService.rejectFriendRequest(loginUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 친구 삭제 API
     *
     * @param session    현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param requestDto 요청 Dto
     * @return HTTP 상태 코드 반환
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(
            HttpSession session,
            @RequestBody @Valid FriendRequestDto requestDto) {

        Long loginUserId = (Long) session.getAttribute("userId");

        friendService.deleteFriend(loginUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 전체친구조회 API
     *
     * @param session    현재 세션에서 로그인한 사용자의 ID 가져옴
     * @param userId 요청자의 Id
     * @return 친구의 Id를 List로 반환
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<FriendResponseDto>> findAllFriends(
            HttpSession session,
            @PathVariable Long userId) {

        Long loginUserId = (Long) session.getAttribute("userId");

        accessWrongValid.validateFriendRequestByUserId(loginUserId, userId);

        List<FriendResponseDto> friends = friendService.findAllFriends(userId);

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }
}



