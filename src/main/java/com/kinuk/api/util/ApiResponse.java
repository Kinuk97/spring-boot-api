package com.kinuk.api.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private static final String RESULT_OK = "ok";
    private static final String RESULT_FAIL = "fail";
    private static final String RESULT_ERROR = "error";

    private String result;
    private T data;
    private String message;

    public static ApiResponse<?> ok() {
        return new ApiResponse<>(RESULT_OK, null, null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(RESULT_OK, data, null);
    }

    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(RESULT_OK, data, message);
    }

    public static <T> ApiResponse<T> ok(String message) {
        return new ApiResponse<>(RESULT_OK, null, message);
    }

    public static ApiResponse<?> fail(String message) {
        return new ApiResponse<>(RESULT_FAIL, null, message);
    }

    public static <T> ApiResponse<T> fail(T data, String message) {
        return new ApiResponse<>(RESULT_FAIL, data, message);
    }

    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>(RESULT_ERROR, null, message);
    }

    public static <T> ApiResponse<T> error(T data, String message) {
        return new ApiResponse<>(RESULT_ERROR, data, message);
    }
}
