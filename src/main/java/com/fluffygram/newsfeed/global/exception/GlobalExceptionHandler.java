package com.fluffygram.newsfeed.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // @valid 유효성 검사 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        // 유효성 검사 오류 메시지를 필드별로 수집
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // 에러 응답 생성
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors);

        log.error("[MethodArgumentNotValidException] : {}", exceptionResponse.getErrors());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleNotFoundException(BusinessException ex) {
        // 오류 메세지 저장
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());

        // 에러 응답 생성
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), ex.getMessage());

        log.error("[ {} ] - NOT_FOUND : {}", ex.getClass(), exceptionResponse.getErrors());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

}
