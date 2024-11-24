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

    // receivedUserId와 sendUserId로 Friend 엔티티를 조회
    Optional<Friend> findByReceivedUserIdAndSendUserId(long loginUserId, long receivedUserId);

    // sendUserId와 receivedUserId로 Friend 엔티티를 조회
    Optional<Friend> findBySendUserIdAndReceivedUserId(Long loginUserId, long receivedUserId);

    // sendUserId와 friendStatus로 Friend 리스트 조회
    List<Friend> findBySendUserIdAndFriendStatus(Long loginUserId, Friend.FriendStatus friendStatus);

    // receivedUserId와 friendStatus로 Friend 리스트 조회
    List<Friend> findByReceivedUserIdAndFriendStatus(Long loginUserId, Friend.FriendStatus friendStatus);

    // sendUser와 receivedUser의 Friend 관계 여부 확인
    boolean existsBySendUserAndReceivedUser(User sendUser, User receivedUser);


    // 친구수락 메서드. 로그인한 유저가 DB ReceivedUserId에 있어야 정상동작. 요청 받은사람이 수락가능.
    default Friend findFriendByReceivedUserIdAndSendUserIdOrThrow(long loginUserId, long receivedUserId) {
        return findByReceivedUserIdAndSendUserId(loginUserId, receivedUserId)
                .orElseThrow(() -> new NotFountByIdException(ExceptionType.FRIEND_NOT_FOUND));
    }

    // sendUserId와 receivedUserId로 Friend 조회, 없으면 예외 발생
    default Friend findBySendUserIdAndReceivedUserIdOrThrow(
            Long loginUserId, long receivedUserId) {
        return findBySendUserIdAndReceivedUserId(loginUserId, receivedUserId)
                .orElseThrow( ()-> new NotFountByIdException(ExceptionType.FRIEND_NOT_FOUND));
    }

    // sendUser와 receivedUser 기준으로 friendStatus에 해당하는 Friend 리스트를 조회, 없으면 빈 리스트 반환
    default List<Friend> findBySendUserAndFriendStatusOrThrow(Long loginUserId, Friend.FriendStatus friendStatus) {

        // 결과를 담을 리스트
        List<Friend> friends = new ArrayList<>();

        // sendUser로 찾은 친구들
        List<Friend> sendUserFriends = findBySendUserIdAndFriendStatus(loginUserId, friendStatus);
        if (sendUserFriends != null && !sendUserFriends.isEmpty()) {
            friends.addAll(sendUserFriends);  // sendUser 조건에 맞는 친구들 추가
        }

        // receivedUser로 찾은 친구들
        List<Friend> receivedUserFriends = findByReceivedUserIdAndFriendStatus(loginUserId, friendStatus);
        if (receivedUserFriends != null && !receivedUserFriends.isEmpty()) {
            friends.addAll(receivedUserFriends);  // receivedUser 조건에 맞는 친구들 추가
        }

        return friends;
    }
}
