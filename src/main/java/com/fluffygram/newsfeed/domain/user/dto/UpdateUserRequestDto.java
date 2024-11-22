package com.fluffygram.newsfeed.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    @NotBlank
    @Size(min = 8, max = 40)
    private final String presentPassword;

    private final String changePassword;

    private final String userNickname;

    private final String phoneNumber;


    public UpdateUserRequestDto(String presentPassword, String changePassword, String userNickname, String phoneNumber) {
        this.presentPassword = presentPassword;
        this.changePassword = changePassword;
        this.userNickname = userNickname;
        this.phoneNumber = phoneNumber;
    }
}
