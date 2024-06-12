package com.kinuk.api.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    ApiResponseCode apiResponseCode;

    public ApiException(ApiResponseCode apiResponseCode) {
        super(apiResponseCode.getMessage());

        this.apiResponseCode = apiResponseCode;
    }
}