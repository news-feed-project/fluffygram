package com.fluffygram.newsfeed.global.exception;

import lombok.Getter;

@Getter
public class WrongAccessException extends BusinessException {
    private final ExceptionType exceptionType;

    public WrongAccessException(final ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }


}
