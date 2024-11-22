package com.fluffygram.newsfeed.domain.user.dto;

import com.fluffygram.newsfeed.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OtherUserResponseDto {
    private final Long id;

    private final String userNickname;

    private final String profileImage;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;


    public static OtherUserResponseDto toDto(User user) {

        return new OtherUserResponseDto(user.getId(), user.getUserNickname(), user.getProfileImage().getDBFileName(), user.getCreatedAt(), user.getModifiedAt());
    }

}
