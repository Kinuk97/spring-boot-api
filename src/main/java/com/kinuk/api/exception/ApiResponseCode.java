package com.kinuk.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiResponseCode {

    // Validation
    INVALID_REQUEST(HttpStatus.OK, "유효성 검증에 실패했습니다."),

    // User
    ALREADY_EXISTS_USER(HttpStatus.OK, "이미 가입된 사용자입니다."),
    LOGIN_FAILED(HttpStatus.OK, "ID 혹은 비밀번호를 잘못 입력하셨거나 등록되지 않은 ID 입니다."),
    USER_NOT_FOUND(HttpStatus.OK, "등록된 회원이 아닙니다."),

    // Jwt Token
    NO_TOKEN(HttpStatus.OK, "Authorization 토큰이 필요합니다."),
    INVALID_TOKEN(HttpStatus.OK, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.OK, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.OK, "지원하지 않는 토큰입니다."),
    EMPTY_TOKEN(HttpStatus.OK, "정보가 비어있는 토큰입니다."),
    EXCEPTION_TOKEN(HttpStatus.OK, "토큰 오류"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}