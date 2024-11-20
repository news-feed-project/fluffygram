package com.fluffygram.newsfeed.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private final String email;

    @NotBlank
    @Size(min = 4, max = 40)
    private final String password;

    @NotBlank
    @Size(min = 1, max = 4)
    private final String userNickname;

    @NotBlank
    private final String phoneNumber;



    public SignUpRequestDto(String email, String password, String userNickname, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.userNickname = userNickname;
        this.phoneNumber = phoneNumber;
    }
}
