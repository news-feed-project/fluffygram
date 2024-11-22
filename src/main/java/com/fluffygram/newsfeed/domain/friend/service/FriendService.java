package com.fluffygram.newsfeed.domain.friend.service;

import com.fluffygram.newsfeed.domain.friend.dto.FriendResponseDto;
import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import com.fluffygram.newsfeed.domain.friend.repository.FriendRepository;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.exception.BusinessException;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
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

    /**
     * 친구 요청 저장
     *
     * @param loginUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    public void sendFriendRequest(long loginUserId, long receivedUserId) {

        if (loginUserId == receivedUserId) {
            throw new BusinessException(ExceptionType.USER_NOT_MATCH);
        }

        User sendUser = userRepository.findByIdOrElseThrow(loginUserId);
        User receivedUser = userRepository.findByIdOrElseThrow(receivedUserId);

        // 중복 여부 확인
        boolean isDuplicate = friendRepository.existsBySendUserAndReceivedUser(sendUser, receivedUser);
        boolean isDuplicate2 = friendRepository.existsBySendUserAndReceivedUser(receivedUser, sendUser);
        if (isDuplicate || isDuplicate2) {
            throw new BusinessException(ExceptionType.EXIST_USER);
        }


        Friend friend = new Friend(sendUser, receivedUser, Friend.FriendStatus.REQUESTED);
        Friend friend2 = new Friend(receivedUser, sendUser, Friend.FriendStatus.REQUESTED);

        friendRepository.save(friend);
        friendRepository.save(friend2);
    }

    /**
     * 친구 요청 수락
     *
     * @param loginUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    @Transactional
    public void acceptFriendRequest(long loginUserId, long receivedUserId) {

        Friend friend = friendRepository.findFriendBySendUserIdAndReceivedUserIdOrThrow(loginUserId, receivedUserId);
        Friend friend2 = friendRepository.findFriendBySendUserIdAndReceivedUserIdOrThrow(receivedUserId, loginUserId);

        friend.acceptFriendRequest();
        friend2.acceptFriendRequest();
    }

    /**
     * 친구 요청 거절
     *
     * @param loginUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    @Transactional
    public void rejectFriendRequest(Long loginUserId, long receivedUserId) {

        Friend friend = friendRepository.findFriendBySendUserIdAndReceivedUserIdOrThrow(loginUserId, receivedUserId);
        Friend friend2 = friendRepository.findFriendBySendUserIdAndReceivedUserIdOrThrow(receivedUserId, loginUserId);

        friend.rejectFriendRequest();
        friend2.rejectFriendRequest();
    }

    /**
     * 친구 삭제
     *
     * @param loginUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    @Transactional
    public void deleteFriend(Long loginUserId, long receivedUserId) {

        Friend friend = friendRepository.findBySendUserIdAndReceivedUserIdOrThrow(
                loginUserId, receivedUserId);
        Friend friend2 = friendRepository.findBySendUserIdAndReceivedUserIdOrThrow(
                receivedUserId, loginUserId);

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

        return friends.stream()
                .map(friend -> new FriendResponseDto(friend.getReceivedUser())) // 친구의 ID만 반환
                .collect(Collectors.toList());
    }
}
