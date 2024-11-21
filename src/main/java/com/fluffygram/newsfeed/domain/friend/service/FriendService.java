package com.fluffygram.newsfeed.domain.friend.service;

import com.fluffygram.newsfeed.domain.friend.entity.Friend;
import com.fluffygram.newsfeed.domain.friend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    //private final UserRepository userRepository;

    public void sendFriendRequest(long sendUserId, long receivedUserId) {
        //User sendUser = userRepository.findById(sendUserId).orElseThrow(() -> new RuntimeException("요청자 ID 못찾음."));
        //User receivedUser = userRepository.findById(receivedUserId).orElseThrow(() -> new RuntimeException("요청받는사람 ID 못찾음."));

//        Friend friend = new Friend(sendUser, receivedUser, FriendStatus.REQUESTED)
//        friendRepository.save(friend);
    }

    // TODO : 친구요청수락 . 재확인
    public void acceptFriendRequest(long sendUserId, long receivedUserId) {
        Friend friend = friendRepository.findBySendUserIdAndReceivedUserId(sendUserId, receivedUserId)
                .orElseThrow( () -> new RuntimeException("친구요청없다.") );

        friend.acceptFriendRequest();
        friendRepository.save(friend);
    }

    // TODO : 친구요청거절
    public void rejectFriendRequest(Long sendUserId, long receivedUserId) {
        Friend friend = friendRepository.findBySendUserIdAndReceivedUserId(sendUserId, receivedUserId)
                .orElseThrow( () -> new RuntimeException("그런요청없다"));

        friend.rejectFriendRequest();
        friendRepository.save(friend);
    }

    // TODO : 친구삭제
    public void deleteFriend(Long sendUserId, long receivedUserId) {
        Friend friend = friendRepository.findBySendUserIdAndReceivedUserIdAndFriendStatus(
                sendUserId, receivedUserId, Friend.FriendStatus.ACCEPTED)
                .orElseThrow( () -> new RuntimeException("친구관계없다."));

        friendRepository.delete(friend);
    }
}
