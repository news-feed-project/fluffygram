package com.fluffygram.newsfeed.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ExceptionType exceptionType;

    public BusinessException(final ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }


}
