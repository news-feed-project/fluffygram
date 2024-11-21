package com.fluffygram.newsfeed.domain.friend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FriendRequestDto {

    // 필드
    @NotBlank
    private long sendUserId;

    @NotBlank
    private long receivedUserId;

    // 생성자
    public FriendRequestDto() {}

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