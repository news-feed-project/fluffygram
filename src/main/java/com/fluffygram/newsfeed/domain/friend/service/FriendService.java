package com.fluffygram.newsfeed.domain.friend.service;

import com.fluffygram.newsfeed.domain.friend.dto.FriendResponseDto;
import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import com.fluffygram.newsfeed.domain.friend.repository.FriendRepository;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotFountByIdException;
import com.fluffygram.newsfeed.global.exception.NotMatchByUserIdException;
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
     * 친구 요청 보내기
     *
     * @param loginUserId       요청을 보낼 사용자 ID
     * @param receivedUserId   요청을 받을 사용자 ID
     *
     */
    public void sendFriendRequest(long loginUserId, long receivedUserId) {

        if (loginUserId == receivedUserId) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        User sendUser = userRepository.findByIdOrElseThrow(loginUserId);
        User receivedUser = userRepository.findByIdOrElseThrow(receivedUserId);

        // 중복 여부 확인
        boolean isDuplicate = friendRepository.existsBySendUserAndReceivedUser(sendUser, receivedUser)
                || friendRepository.existsBySendUserAndReceivedUser(receivedUser, sendUser);

        if (isDuplicate) {
            throw new NotMatchByUserIdException(ExceptionType.EXIST_USER);
        }


        Friend friend = new Friend(sendUser, receivedUser, Friend.FriendStatus.REQUESTED);

        friendRepository.save(friend);
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

        if (loginUserId == receivedUserId) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        Friend friend = friendRepository.findFriendByReceivedUserIdAndSendUserIdOrThrow(loginUserId, receivedUserId);

        friend.acceptFriendRequest();
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

        if (loginUserId == receivedUserId) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }

        Friend friend = friendRepository.findFriendByReceivedUserIdAndSendUserIdOrThrow(loginUserId, receivedUserId);

        friend.rejectFriendRequest();
    }

    /**
     * 친구 데이터베이스에서 삭제
     *
     * @param loginUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    @Transactional
    public void deleteFriend(Long loginUserId, long receivedUserId) {

        if (loginUserId == receivedUserId) {
            throw new NotMatchByUserIdException(ExceptionType.USER_NOT_MATCH);
        }


        if (friendRepository.findBySendUserIdAndReceivedUserIdOrThrow(loginUserId, receivedUserId) != null) {
            Friend friend = friendRepository.findBySendUserIdAndReceivedUserIdOrThrow(loginUserId, receivedUserId);
            friendRepository.delete(friend);
        } else if (friendRepository.findBySendUserIdAndReceivedUserIdOrThrow(receivedUserId, loginUserId) != null) {
            Friend friend = friendRepository.findBySendUserIdAndReceivedUserIdOrThrow(receivedUserId, loginUserId);
            friendRepository.delete(friend);
        } else {
            throw new NotFountByIdException(ExceptionType.FRIEND_NOT_FOUND);
        }

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
                .map(friend -> {
                    User friendUser = friend.getSendUser().getId().equals(userId)
                            ? friend.getReceivedUser() // sendUser 가 userId일 때는 receivedUser
                            : friend.getSendUser(); // receivedUser 가 userId일 때는 sendUser
                    return new FriendResponseDto(friendUser);  // FriendResponseDto 생성
                })
                .collect(Collectors.toList());  // 결과 리스트 반환
    }
}
