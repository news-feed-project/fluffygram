package com.fluffygram.newsfeed.domain.friend.controller;

import com.fluffygram.newsfeed.domain.friend.dto.FriendRequestDto;
import com.fluffygram.newsfeed.domain.friend.service.FriendService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    // 의존성 자동주입
    private final FriendService friendService;

    /**
     * 친구 요청 API
     *
     * @param session
     * @param requestDto 요청 Dto
     * @return 성공시 201 Created 응답코드
     *
     * 사용자가 다른사용자에게 친구요청을 하는 API
     * 요청본문(JSON) 에는 요청을 보내는 유저의 id와 요청을 받는 유저의 id를 포함해야함
     * 요청 정상 처리시 201 CREATED 상태코드 반환
     */
    @PostMapping
    public ResponseEntity<Void> sendFriendRequest(
            HttpSession session,
            @RequestBody FriendRequestDto requestDto) {

        Long sendUserId = (Long) session.getAttribute("userId");

        if (sendUserId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

//        long sendUserId = requestDto.getSendUserId();
        long receivedUserId = requestDto.getReceivedUserId();

        friendService.sendFriendRequest(sendUserId, receivedUserId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO : 친구요청수락 API. API문서에는 요청데이터가 없는걸로 나오는데,어째야할지? 이게 맞는거같기도하고.
    @GetMapping("/accept")
    public ResponseEntity<Void> acceptFriendRequest(
            HttpSession session,
            @RequestBody FriendRequestDto requestDto) {

        Long sendUserId = (Long) session.getAttribute("userId");

        if (sendUserId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        friendService.acceptFriendRequest(sendUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO : 친구요청거절 API 만들기.
    @GetMapping("/reject")
    public ResponseEntity<Void> rejectFriendRequest(
            HttpSession session,
            @RequestBody FriendRequestDto requestDto) {

        Long sendUserId = (Long) session.getAttribute("userId");

        if (sendUserId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        friendService.rejectFriendRequest(sendUserId, requestDto.getReceivedUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO : 모든 친구 조회 API 만들기. List타입 반환 유저가 있어야할듯?
    @GetMapping("/{userId}")
    public ResponseEntity<Void> findAllFriends(@PathVariable Long userId) {

        return null;
    }

    // TODO : 친구삭제 API
    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(
            HttpSession session,
            @RequestBody FriendRequestDto requestDto) {

        Long sendUserId = (Long) session.getAttribute("userId");
        if (sendUserId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        long receivedUserId = requestDto.getReceivedUserId();

        friendService.deleteFriend(sendUserId, receivedUserId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


// 친구요청 거절하면

// 데이터베이스에 1,2 / 2,1 같이넣기


