package com.fluffygram.newsfeed.domain.user.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    REGISTER(1, "가입"), DELETE(2, "탈퇴"), ADMIN(0, "관리자");


    private final int statusNumber;
    private final String statusText;

    UserStatus(int statusNumber, String statusText) {
        this.statusNumber = statusNumber;
        this.statusText = statusText;
    }
}
