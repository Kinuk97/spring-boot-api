package com.kinuk.api.exception;

import lombok.Getter;

@Getter
public class JwtTokenException extends RuntimeException {

    ApiResponseCode apiResponseCode;

    public JwtTokenException(ApiResponseCode apiResponseCode) {
        super(apiResponseCode.getMessage());

        this.apiResponseCode = apiResponseCode;
    }
}
