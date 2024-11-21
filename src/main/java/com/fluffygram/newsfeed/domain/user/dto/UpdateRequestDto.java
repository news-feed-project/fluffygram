package com.fluffygram.newsfeed.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateRequestDto {

    @NotBlank
    @Size(min = 8, max = 40)
    private final String presentPassword;

    @NotBlank
    @Size(min = 8, max = 40)
    private final String ChangePassword;

    @NotBlank
    @Size(min = 1, max = 4)
    private final String userNickname;

    @NotBlank
    private final String phoneNumber;


    public UpdateRequestDto(String presentPassword, String changePassword, String userNickname, String phoneNumber) {
        this.presentPassword = presentPassword;
        ChangePassword = changePassword;
        this.userNickname = userNickname;
        this.phoneNumber = phoneNumber;
    }
}
