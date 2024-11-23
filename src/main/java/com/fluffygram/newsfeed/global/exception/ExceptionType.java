package com.fluffygram.newsfeed.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    EXIST_USER(HttpStatus.BAD_REQUEST, "동일한 email 의 사용자가 존재합니다."),
    PASSWORD_NOT_CORRECT(HttpStatus.BAD_REQUEST,  "비밀번호가 일치하지 않습니다."),
    PASSWORD_SAME(HttpStatus.BAD_REQUEST, "기존의 비밀번호와 일치합니다."),
    DELETED_USER(HttpStatus.BAD_REQUEST, "이미 삭제된 유저입니다."),
    USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 유저의 정보에 접근하고 있습니다."),
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 파일을 찾을 수 없습니다."),
    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 파일을 찾을 수 없습니다."),
    NOT_LOGIN(HttpStatus.UNAUTHORIZED, "로그인을 해주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,  "해당 유저의 정보를 찾을 수 없습니다."),
    FAIL_FILE_UPLOADED(HttpStatus.INTERNAL_SERVER_ERROR,  "해당 파일 업로드에 실패하였습니다. 잘못된 형식의 확장자입니다."),
    FAIL_FILE_DOWNLOADED(HttpStatus.INTERNAL_SERVER_ERROR,  "해당 파일 다운로드에 실패하였습니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionType(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
