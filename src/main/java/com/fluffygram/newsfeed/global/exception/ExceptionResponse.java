package com.fluffygram.newsfeed.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private LocalDateTime dateTime;
    private HttpStatus httpStatus;
    private int statusCode;
    private String message;
    private Map<String, String> errors;


    ExceptionResponse(HttpStatus httpStatus, int statusCode, String message) {
        this.dateTime = LocalDateTime.now();
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.message = message;
    }

    ExceptionResponse(LocalDateTime dateTime, HttpStatus httpStatus, int statusCode, Map<String, String> errors) {
        this.dateTime = dateTime;
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.errors = errors;
    }
}
