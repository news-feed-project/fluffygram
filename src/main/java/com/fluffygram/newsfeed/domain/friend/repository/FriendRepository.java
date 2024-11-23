package com.fluffygram.newsfeed.domain.friend.repository;

import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import com.fluffygram.newsfeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByReceivedUserIdAndSendUserId(long receivedUserId, long sendUserId);

    Optional<Friend> findBySendUserIdAndReceivedUserId(
            Long sendUserId, long receivedUserId);

    List<Friend> findBySendUser_IdAndFriendStatus(Long userId, Friend.FriendStatus friendStatus);

    List<Friend> findByReceivedUserIdAndFriendStatus(Long userId, Friend.FriendStatus friendStatus);


    default Friend findFriendByReceivedUserIdAndSendUserIdOrThrow(long receivedUserId, long sendUserId) {
        return findByReceivedUserIdAndSendUserId(receivedUserId, sendUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "친구 요청을 찾을 수 없습니다."
                ));
    }

    default Friend findBySendUserIdAndReceivedUserIdOrThrow(
            Long sendUserId, long receivedUserId) {
        return findBySendUserIdAndReceivedUserId(sendUserId, receivedUserId)
                .orElseThrow( ()-> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "데이터를 찾을수 없습니다."
                ) );
    }

    default List<Friend> findBySendUserAndFriendStatusOrThrow(Long userId, Friend.FriendStatus friendStatus) {

        // 결과를 담을 리스트
        List<Friend> friends = new ArrayList<>();

        // sendUser로 찾은 친구들
        List<Friend> sendUserFriends = findBySendUser_IdAndFriendStatus(userId, friendStatus);
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
