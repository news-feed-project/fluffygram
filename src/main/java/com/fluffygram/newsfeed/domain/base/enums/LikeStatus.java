package com.fluffygram.newsfeed.domain.base.enums;

import lombok.Getter;

@Getter
public enum LikeStatus {
    REGISTER(1, "활성화"), DELETE(0, "비활성화");

    private final int statusNumber;
    private final String statusText;

    LikeStatus(int statusNumber, String statusText) {
        this.statusNumber = statusNumber;
        this.statusText = statusText;
    }
}
