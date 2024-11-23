package com.fluffygram.newsfeed.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class FriendResponseDto {

    private final Long friendId;

    public FriendResponseDto(Long friendId) {
        this.friendId = friendId;
    }
}
