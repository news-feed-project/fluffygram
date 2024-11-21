package com.fluffygram.newsfeed.domain.friend.entity;

import com.fluffygram.newsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id")
    private User sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_user_id")
    private User receivedUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "friend_status", nullable = false, columnDefinition = "ENUM('REQUESTED', 'ACCEPTED', 'NOT_FRIEND') DEFAULT 'NOT_FRIEND'")
    private FriendStatus friendStatus = FriendStatus.NOT_FRIEND;

    @Column(name = "request_at", nullable = false)
    private LocalDateTime requestAt;

    @Column(name = "accept_at", nullable = false)
    private LocalDateTime acceptAt;

    public enum FriendStatus {
        REQUESTED, // 요청중상태
        ACCEPTED, // 친구상태
        NOT_FRIEND // 친구아님상태
    }

    public Friend() { }

//    // 사용자로부터 친구 요청을 받는 생성자
//    public Friend(User sendUser, User receivedUser, FriendStatus friendStatus) {
//        this.sendUser = sendUser;
//        this.receivedUser = receivedUser;
//        this.friendStatus = friendStatus;
//        this.requestAt = LocalDateTime.now(); // 친구 요청 시간. 현재시간.
//    }

    // 생성자를 통해 "친구요청상태" / "수락상태" 관리
    public void acceptFriendRequest() {
        this.friendStatus = FriendStatus.ACCEPTED;
        this.acceptAt = LocalDateTime.now(); // 친구 수락 시간. 현재시간.
    }

    public void rejectFriendRequest() {
        this.friendStatus = FriendStatus.NOT_FRIEND;
        this.acceptAt = LocalDateTime.now();
    }

}