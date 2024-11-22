package com.fluffygram.newsfeed.domain.friend.repository;

import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findBySendUserIdAndReceivedUserId(long sendUserId, long receivedUserId);

    Optional<Friend> findBySendUserIdAndReceivedUserIdAndFriendStatus(
            Long sendUserId, long receivedUserId, Friend.FriendStatus friendStatus);

    /*
    SELECT *
    FROM friend
    WHERE send_user_id = :userId
    AND friend_status = :friendStatus;
    */
    List<Friend> findBySendUser_IdAndFriendStatus(Long userId, Friend.FriendStatus friendStatus);



    default Friend findFriendBySendUserIdAndReceivedUserIdOrThrow(long sendUserId, long receivedUserId) {
        return findBySendUserIdAndReceivedUserId(sendUserId, receivedUserId)
                .orElseThrow(() -> new RuntimeException("친구 요청을 찾을 수 없습니다."));
    }

    default Friend findBySendUserIdAndReceivedUserIdAndFriendStatusOrThrow(
            Long sendUserId, long receivedUserId, Friend.FriendStatus friendStatus) {
        return findBySendUserIdAndReceivedUserIdAndFriendStatus(sendUserId, receivedUserId, friendStatus)
                .orElseThrow( ()-> new RuntimeException("현재 이미 둘이 친구가 아닙니다.") );
    }

    default List<Friend> findBySendUserAndFriendStatusOrThrow(Long userId, Friend.FriendStatus friendStatus) {

        List<Friend> friends = findBySendUser_IdAndFriendStatus(userId, friendStatus);

        if (friends.isEmpty()) {
            throw new RuntimeException("친구가 없습니다.");
        }

        return friends;
    }

}
