package com.kinuk.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiResponseCode {

    ALREADY_EXISTS_USER(HttpStatus.OK, "이미 가입된 사용자입니다."),
    INVALID_REQUEST(HttpStatus.OK, "유효성 검증에 실패했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}