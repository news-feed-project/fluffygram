package com.fluffygram.newsfeed.domain.friend.dto;

import com.fluffygram.newsfeed.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FriendResponseDto {

    private final String userNickname;
    private final Long id;
    private final String email;


    public FriendResponseDto(User receivedUser) {
        this.id = receivedUser.getId();
        this.userNickname = receivedUser.getUserNickname();
        this.email = receivedUser.getEmail();
    }
}
