package com.fluffygram.newsfeed.global.exception;

import lombok.Getter;

@Getter
public class BadValueException extends RuntimeException {
    private final ExceptionType exceptionType;

    public BadValueException(final ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }


}