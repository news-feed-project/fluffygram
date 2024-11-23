package com.fluffygram.newsfeed.global.exception;

import lombok.Getter;

@Getter
public class NotMatchByUserIdException extends RuntimeException {
    private final ExceptionType exceptionType;

    public NotMatchByUserIdException(final ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }


}
