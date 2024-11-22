package com.fluffygram.newsfeed.domain.friend.service;

import com.fluffygram.newsfeed.domain.base.Valid.AccessWrongValid;
import com.fluffygram.newsfeed.domain.friend.dto.FriendRequestDto;
import com.fluffygram.newsfeed.domain.friend.dto.FriendResponseDto;
import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import com.fluffygram.newsfeed.domain.friend.repository.FriendRepository;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final AccessWrongValid accessWrongValid;

    /**
     * 친구 요청 저장
     *
     * @param sendUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    public void sendFriendRequest(long sendUserId, long receivedUserId) {

        accessWrongValid.validateFriendRequestDto(sendUserId, new FriendRequestDto(sendUserId, receivedUserId));

        User sendUser = userRepository.findByIdOrElseThrow(sendUserId);
        User receivedUser = userRepository.findByIdOrElseThrow(receivedUserId);

        Friend friend = new Friend(sendUser, receivedUser, Friend.FriendStatus.REQUESTED);
        Friend friend2 = new Friend(receivedUser, sendUser, Friend.FriendStatus.REQUESTED);

        friendRepository.save(friend);
        friendRepository.save(friend2);
    }

    /**
     * 친구 요청 수락
     *
     * @param sendUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    @Transactional
    public void acceptFriendRequest(long sendUserId, long receivedUserId) {

        accessWrongValid.validateFriendRequestDto(sendUserId, new FriendRequestDto(sendUserId, receivedUserId));

        Friend friend = friendRepository.findFriendBySendUserIdAndReceivedUserIdOrThrow(sendUserId, receivedUserId);
        Friend friend2 = friendRepository.findFriendBySendUserIdAndReceivedUserIdOrThrow(receivedUserId, sendUserId);

        friend.acceptFriendRequest();
        friend2.acceptFriendRequest();
    }

    /**
     * 친구 요청 거절
     *
     * @param sendUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    @Transactional
    public void rejectFriendRequest(Long sendUserId, long receivedUserId) {

        accessWrongValid.validateFriendRequestDto(sendUserId, new FriendRequestDto(sendUserId, receivedUserId));

        Friend friend = friendRepository.findFriendBySendUserIdAndReceivedUserIdOrThrow(sendUserId, receivedUserId);
        Friend friend2 = friendRepository.findFriendBySendUserIdAndReceivedUserIdOrThrow(receivedUserId, sendUserId);

        friend.rejectFriendRequest();
        friend2.rejectFriendRequest();
    }

    /**
     * 친구 삭제
     *
     * @param sendUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    @Transactional
    public void deleteFriend(Long sendUserId, long receivedUserId) {

        accessWrongValid.validateFriendRequestDto(sendUserId, new FriendRequestDto(sendUserId, receivedUserId));

        Friend friend = friendRepository.findBySendUserIdAndReceivedUserIdAndFriendStatusOrThrow(
                sendUserId, receivedUserId, Friend.FriendStatus.ACCEPTED);

        Friend friend2 = friendRepository.findBySendUserIdAndReceivedUserIdAndFriendStatusOrThrow(
                        receivedUserId, sendUserId, Friend.FriendStatus.ACCEPTED);

        friendRepository.delete(friend);
        friendRepository.delete(friend2);
    }

    /**
     * 전체친구조회
     *
     * @param userId       요청을 보낸 사용자 ID
     * @return List<FriendResponseDto>
     */
    public List<FriendResponseDto> findAllFriends(Long userId) {

        List<Friend> friends = friendRepository.findBySendUserAndFriendStatusOrThrow(userId, Friend.FriendStatus.ACCEPTED);

        if (friends.isEmpty()) {
            throw new RuntimeException("친구가 없습니다.");
        }

        return friends.stream()
                .map(friend -> new FriendResponseDto(friend.getReceivedUser().getId())) // 친구의 ID만 반환
                .collect(Collectors.toList());
    }
}
