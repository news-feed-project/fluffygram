package com.fluffygram.newsfeed.domain.user.dto;

import com.fluffygram.newsfeed.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private final Long id;

    private final String email;

    private final String userNickname;

    private final String phoneNumber;

    private final String profileImage;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;


    public static UserResponseDto toDto(User user) {
        // String userImage = GetUserImage.getUserImage(user.getProfileImage());
        String userImage = user.getProfileImage().getDBFileName();

        return new UserResponseDto(user.getId(), user.getEmail(), user.getUserNickname(), user.getPhoneNumber(), userImage, user.getCreatedAt(), user.getModifiedAt());
    }
}
