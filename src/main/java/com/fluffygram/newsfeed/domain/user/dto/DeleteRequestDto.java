package com.fluffygram.newsfeed.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DeleteRequestDto {

    @NotBlank
    @Size(min = 8, max = 40)
    private String password;

    DeleteRequestDto(){}

    public DeleteRequestDto(String password) {
        this.password = password;
    }
}
