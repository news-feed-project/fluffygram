package com.fluffygram.newsfeed.domain.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FriendRequestDto {

    // 필드
    @NotNull(message = "요청유저 ID는 필수입니다.")
    private long sendUserId;

    @NotNull(message = "요청받는유저 ID는 필수입니다.")
    private long receivedUserId;

    // 생성자
    public FriendRequestDto() {}

    public FriendRequestDto(long sendUserId, long receivedUserId) {
        this.sendUserId = sendUserId;
        this.receivedUserId = receivedUserId;
    }

    // Getter , Setter
    public long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public long getReceivedUserId() {
        return receivedUserId;
    }

    public void setReceivedUserId(long receivedUserId) {
        this.receivedUserId = receivedUserId;
    }
}