package com.fluffygram.newsfeed.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserStatus {
    REGISTER(1, "가입"), DELETE(0, "탈퇴");


    private final int statusNumber;
    private final String statusText;

    UserStatus(int statusNumber, String statusText) {
        this.statusNumber = statusNumber;
        this.statusText = statusText;
    }
}
