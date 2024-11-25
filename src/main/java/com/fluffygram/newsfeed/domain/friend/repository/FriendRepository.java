package com.fluffygram.newsfeed.domain.friend.repository;

import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.global.exception.ExceptionType;
import com.fluffygram.newsfeed.global.exception.NotFountByIdException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByReceivedUserIdAndSendUserId(long receivedUserId, long sendUserId);

    Optional<Friend> findBySendUserIdAndReceivedUserId(Long sendUserId, long receivedUserId);

    List<Friend> findBySendUserIdAndFriendStatus(Long userId, Friend.FriendStatus friendStatus);

    List<Friend> findByReceivedUserIdAndFriendStatus(Long userId, Friend.FriendStatus friendStatus);


    default Friend findFriendByReceivedUserIdAndSendUserIdOrThrow(long receivedUserId, long sendUserId) {
        return findByReceivedUserIdAndSendUserId(receivedUserId, sendUserId)
                .orElseThrow(() -> new NotFountByIdException(ExceptionType.FRIEND_NOT_FOUND));
    }

    default Friend findBySendUserIdAndReceivedUserIdOrThrow(
            Long sendUserId, long receivedUserId) {
        return findBySendUserIdAndReceivedUserId(sendUserId, receivedUserId)
                .orElseThrow( ()-> new NotFountByIdException(ExceptionType.FRIEND_NOT_FOUND));
    }

    default List<Friend> findBySendUserAndFriendStatusOrThrow(Long userId, Friend.FriendStatus friendStatus) {

        // 결과를 담을 리스트
        List<Friend> friends = new ArrayList<>();

        // sendUser로 찾은 친구들
        List<Friend> sendUserFriends = findBySendUserIdAndFriendStatus(userId, friendStatus);
        if (sendUserFriends != null && !sendUserFriends.isEmpty()) {
            friends.addAll(sendUserFriends);  // sendUser 조건에 맞는 친구들 추가
        }

        // receivedUser로 찾은 친구들
        List<Friend> receivedUserFriends = findByReceivedUserIdAndFriendStatus(userId, friendStatus);
        if (receivedUserFriends != null && !receivedUserFriends.isEmpty()) {
            friends.addAll(receivedUserFriends);  // receivedUser 조건에 맞는 친구들 추가
        }

        return friends;
    }

    boolean existsBySendUserAndReceivedUser(User sendUser, User receivedUser);

}
