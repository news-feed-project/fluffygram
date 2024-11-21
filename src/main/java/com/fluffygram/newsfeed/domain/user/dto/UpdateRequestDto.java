package com.fluffygram.newsfeed.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateRequestDto {

    @NotBlank
    @Size(min = 8, max = 40)
    private final String presentPassword;

    private final String ChangePassword;

    private final String userNickname;

    private final String phoneNumber;


    public UpdateRequestDto(String presentPassword, String changePassword, String userNickname, String phoneNumber) {
        this.presentPassword = presentPassword;
        ChangePassword = changePassword;
        this.userNickname = userNickname;
        this.phoneNumber = phoneNumber;
    }
}
