package com.fluffygram.newsfeed.domain.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FriendAcceptRequestDto {

    @NotNull(message = "요청받는유저 ID는 필수입니다.")
    private long sendUserId;

    // 생성자
    public FriendAcceptRequestDto() {}

    public FriendAcceptRequestDto(long sendUserId) {
        this.sendUserId = sendUserId;
    }
}