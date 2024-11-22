package com.fluffygram.newsfeed.domain.friend.repository;

import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    // SendUserId 랑 ReceivedUserId 둘다 있는거 찾는 쿼리
    // 친구요청 수락 , 거절할때 쓰이는 코드.
    //Optional<Friend> findBySendUserIdAndReceivedUserId(long sendUserId, long receivedUserId);

    Optional<Friend> findBySendUserIdAndReceivedUserId(long sendUserId, long receivedUserId);

    default Friend findFriendBySendUserIdAndReceivedUserIdOrThrow(long sendUserId, long receivedUserId) {
        return findBySendUserIdAndReceivedUserId(sendUserId, receivedUserId)
                .orElseThrow(() -> new RuntimeException("친구 요청을 찾을 수 없습니다."));
    }

    // 친구 관계가 ACCEPTED 상태인 관계를 찾는 쿼리 메서드
    // 삭제할때 쓰이고있음.
//    Optional<Friend> findBySendUserIdAndReceivedUserIdAndFriendStatus(
//            Long sendUserId, Long receivedUserId, Friend.FriendStatus friendStatus);

    Optional<Friend> findBySendUserIdAndReceivedUserIdAndFriendStatus(
            Long sendUserId, long receivedUserId, Friend.FriendStatus friendStatus);

    default Friend findBySendUserIdAndReceivedUserIdAndFriendStatusOrThrow(
            Long sendUserId, long receivedUserId, Friend.FriendStatus friendStatus) {
        return findBySendUserIdAndReceivedUserIdAndFriendStatus(sendUserId, receivedUserId, friendStatus)
                .orElseThrow( ()-> new RuntimeException("현재 이미 둘이 친구가 아닙니다.") );
    }


}
