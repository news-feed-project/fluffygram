package com.fluffygram.newsfeed.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {

    EXIST_USER(400, "USER" , "동일한 email 의 사용자가 존재합니다."),
    PASSWORD_NOT_CORRECT(400, "USER" , "비밀번호가 일치하지 않습니다."),
    PASSWORD_SAME(400, "USER" , "기존의 비밀번호와 일치합니다."),
    DELETED_USER(400, "USER", "이미 삭제된 유저입니다."),
    USER_NOT_FOUND(404, "USER", "해당 유저의 정보를 찾을 수 없습니다."),
    USER_NOT_MATCH(400, "USER", "잘못된 유저의 정보에 접근하고 있습니다."),
    FILE_NOT_FOUND(400, "FILE", "해당 파일을 찾을 수 없습니다."),
    FAIL_FILE_UPLOADED(500, "FILE", "해당 파일 업로드에 실패하였습니다."),
    FAIL_FILE_DOWNLOADED(500, "FILE", "해당 파일 다운로드에 실패하였습니다.");

    private final int status;
    private final String type;
    private final String message;

    ExceptionType(int status, String type, String message) {
        this.status = status;
        this.type = type;
        this.message = message;
    }
}
