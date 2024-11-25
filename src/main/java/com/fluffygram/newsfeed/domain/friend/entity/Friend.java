package com.fluffygram.newsfeed.domain.friend.entity;

import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import com.fluffygram.newsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
@Table(name = "friend")
public class Friend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id")
    @NotNull
    private User sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_user_id")
    @NotNull
    private User receivedUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "friend_status", columnDefinition = "ENUM('REQUESTED', 'ACCEPTED')")
    @NotNull
    private FriendStatus friendStatus;

    public enum FriendStatus {
        REQUESTED, // 요청중상태
        ACCEPTED // 친구상태
    }

    public Friend() { }

    // 사용자로부터 친구 요청을 받는 생성자
    public Friend(User sendUser, User receivedUser, FriendStatus friendStatus) {
        this.sendUser = sendUser;
        this.receivedUser = receivedUser;
        this.friendStatus = friendStatus;
    }

    // 생성자를 통해 "친구요청상태" / "수락상태" 관리
    public void acceptFriendRequest() {
        if ( this.friendStatus == FriendStatus.ACCEPTED ) {
            throw new IllegalStateException("이미 친구입니다.");
        }
        this.friendStatus = FriendStatus.ACCEPTED;
    }
}
