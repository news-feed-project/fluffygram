package com.fluffygram.newsfeed.global.exception;

import lombok.Getter;

@Getter
public class NotFountByIdException extends RuntimeException {
    private final ExceptionType exceptionType;

    public NotFountByIdException(final ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }


}