package com.fluffygram.newsfeed.domain.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FriendRequestDto {

    @NotNull(message = "요청받는유저 ID는 필수입니다.")
    private long receivedUserId;

    // 생성자
    public FriendRequestDto() {}

    public FriendRequestDto(long receivedUserId) {
        this.receivedUserId = receivedUserId;
    }
}