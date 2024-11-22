package com.fluffygram.newsfeed.domain.user.dto;

import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.enums.UserRelationship;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private final Long id;

    private String email;

    private final String userNickname;

    private String phoneNumber;

    private final String profileImage;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    @Setter
    private String status;

    public UserResponseDto(Long id, String email, String userNickname, String phoneNumber, String profileImage, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.userNickname = userNickname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
        this.updatedAt = modifiedAt;
        this.status = UserRelationship.MINE.toString();
    }

    public UserResponseDto(Long id, String userNickname, String userImage, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userNickname = userNickname;
        this.profileImage = userImage;
        this.createdAt = createdAt;
        this.updatedAt = modifiedAt;
    }


    public static UserResponseDto ToDtoForMine(User user) {
        // String userImage = GetUserImage.getUserImage(user.getProfileImage());
        String userImage = user.getProfileImage().getDBFileName();

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getUserNickname(),
                user.getPhoneNumber(),
                userImage,
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    public static UserResponseDto ToDtoForOther(User user) {
        // String userImage = GetUserImage.getUserImage(user.getProfileImage());
        String userImage = user.getProfileImage().getDBFileName();

        return new UserResponseDto(
                user.getId(),
                user.getUserNickname(),
                userImage,
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

}
