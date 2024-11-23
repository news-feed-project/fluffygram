package com.fluffygram.newsfeed.domain.user.dto;

import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.base.enums.UserRelationship;
import com.fluffygram.newsfeed.global.config.Const;
import com.fluffygram.newsfeed.global.tool.GetImage;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

    public UserResponseDto(Long id, String userNickname, String base64Image, LocalDateTime createdAt, LocalDateTime modifiedAt, UserRelationship userRelationship) {
        this.id = id;
        this.userNickname = userNickname;
        this.profileImage = base64Image;
        this.createdAt = createdAt;
        this.updatedAt = modifiedAt;
        this.status = userRelationship.toString();
    }


    public static UserResponseDto ToDtoForMine(User user) {
        String base64Image = GetImage.getImage(Const.USER_IMAGE_STORAGE, user.getProfileImage().getDBFileName());

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getUserNickname(),
                user.getPhoneNumber(),
                base64Image,
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    public static UserResponseDto ToDtoForOther(User user) {
        String base64Image = GetImage.getImage(Const.USER_IMAGE_STORAGE, user.getProfileImage().getDBFileName());

        return new UserResponseDto(
                user.getId(),
                user.getUserNickname(),
                base64Image,
                user.getCreatedAt(),
                user.getModifiedAt(),
                UserRelationship.OTHER
        );
    }

}
