package com.fluffygram.newsfeed.domain.friend.repository;

import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    // SendUserId 랑 ReceivedUserId 둘다 있는거 찾는 쿼리
    Optional<Friend> findBySendUserIdAndReceivedUserId(long sendUserId, long receivedUserId);

    // 친구 관계가 ACCEPTED 상태인 관계를 찾는 쿼리 메서드
    Optional<Friend> findBySendUserIdAndReceivedUserIdAndFriendStatus(
            Long sendUserId, Long receivedUserId, Friend.FriendStatus friendStatus);
}
