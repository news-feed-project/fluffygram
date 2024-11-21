package com.fluffygram.newsfeed.domain.friend.entity;

import com.fluffygram.newsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotBlank
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id")
    @NotBlank
    private User sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_user_id")
    @NotBlank
    private User receivedUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "friend_status", columnDefinition = "ENUM('REQUESTED', 'ACCEPTED', 'NOT_FRIEND') DEFAULT 'NOT_FRIEND'")
    @NotBlank
    private FriendStatus friendStatus = FriendStatus.NOT_FRIEND;

    @Column(name = "request_at")
    @NotBlank
    private LocalDateTime requestAt;

    @Column(name = "accept_at")
    @NotBlank
    private LocalDateTime acceptAt;

    public enum FriendStatus {
        REQUESTED, // 요청중상태
        ACCEPTED, // 친구상태
        NOT_FRIEND // 친구아님상태
    }

    public Friend() { }

    // 사용자로부터 친구 요청을 받는 생성자
    public Friend(User sendUser, User receivedUser, FriendStatus friendStatus) {
        this.sendUser = sendUser;
        this.receivedUser = receivedUser;
        this.friendStatus = friendStatus;
        this.requestAt = LocalDateTime.now(); // 친구 요청 시간. 현재시간.
    }

    // 생성자를 통해 "친구요청상태" / "수락상태" 관리
    public void acceptFriendRequest() {
        this.friendStatus = FriendStatus.ACCEPTED;
        this.acceptAt = LocalDateTime.now(); // 친구 수락 시간. 현재시간.
    }

    // 친구관계 삭제시. NOT_FRIEND
    public void rejectFriendRequest() {
        this.friendStatus = FriendStatus.NOT_FRIEND;
        this.acceptAt = LocalDateTime.now();
    }

}
