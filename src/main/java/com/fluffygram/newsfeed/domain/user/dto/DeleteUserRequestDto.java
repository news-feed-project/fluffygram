package com.fluffygram.newsfeed.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DeleteUserRequestDto {

    @NotBlank
    @Size(min = 8, max = 40)
    private String password;

    DeleteUserRequestDto(){}

    public DeleteUserRequestDto(String password) {
        this.password = password;
    }
}
