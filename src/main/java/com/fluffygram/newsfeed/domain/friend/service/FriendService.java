package com.fluffygram.newsfeed.domain.friend.service;

import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import com.fluffygram.newsfeed.domain.friend.repository.FriendRepository;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    /**
     * 친구 요청 저장
     *
     * @param sendUserId       요청을 보낸 사용자 ID
     * @param receivedUserId   요청을 받은 사용자 ID
     *
     */
    public void sendFriendRequest(long sendUserId, long receivedUserId) {

        User sendUser = userRepository.findById(sendUserId)
                .orElseThrow(() -> new RuntimeException("요청자 ID 못찾음."));

        User receivedUser = userRepository.findById(receivedUserId)
                .orElseThrow(() -> new RuntimeException("요청받는사람 ID 못찾음."));

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
        Friend friend = friendRepository.findBySendUserIdAndReceivedUserId(sendUserId, receivedUserId)
                .orElseThrow( () -> new RuntimeException("친구요청없다.") );
        Friend friend2 = friendRepository.findBySendUserIdAndReceivedUserId(receivedUserId, sendUserId)
                .orElseThrow( () -> new RuntimeException("친구요청없다.") );

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
        Friend friend = friendRepository.findBySendUserIdAndReceivedUserId(sendUserId, receivedUserId)
                .orElseThrow( () -> new RuntimeException("그런요청없다"));
        Friend friend2 = friendRepository.findBySendUserIdAndReceivedUserId(receivedUserId, sendUserId)
                .orElseThrow( () -> new RuntimeException("그런요청없다"));

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
        Friend friend = friendRepository.findBySendUserIdAndReceivedUserIdAndFriendStatus(
                sendUserId, receivedUserId, Friend.FriendStatus.ACCEPTED)
                .orElseThrow( () -> new RuntimeException("둘이 친구가 아니거나 그런친구관계없다."));
        Friend friend2 = friendRepository.findBySendUserIdAndReceivedUserIdAndFriendStatus(
                        receivedUserId, sendUserId, Friend.FriendStatus.ACCEPTED)
                .orElseThrow( () -> new RuntimeException("둘이 친구가 아니거나 그런친구관계없다."));

        friendRepository.delete(friend);
        friendRepository.delete(friend2);
    }
}
