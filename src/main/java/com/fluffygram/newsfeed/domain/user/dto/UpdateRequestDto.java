package com.fluffygram.newsfeed.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateRequestDto {

    @NotBlank
    @Size(min = 4, max = 40)
    private final String password;

    @NotBlank
    @Size(min = 1, max = 4)
    private final String userNickname;

    @NotBlank
    private final String phoneNumber;


    public UpdateRequestDto(String password, String userNickname, String phoneNumber) {
        this.password = password;
        this.userNickname = userNickname;
        this.phoneNumber = phoneNumber;
    }
}
