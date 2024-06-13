package com.kinuk.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class LoginDto {

    @Data
    public static class Request {
        @NotBlank
        @Schema(description = "사용자 아이디", defaultValue = "kw68")
        private String userId;

        @NotBlank
        @Schema(description = "사용자 비밀번호", defaultValue = "123456")
        private String password;
    }

    @Data
    @Builder
    public static class Response {
        @Schema(description = "Jwt 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrdzY4IiwiaWF0IjoxNzE4MjcwNjE0LCJleHAiOjE3MTgzNTcwMTR9.wdud5blcMJ4tf_H5Ld58ynZPWmEousNBxf676UdMbZw2oTtQHDN8PXSYTKBfR0Iorw_vh3nDDecueG1cEzihDg")
        private String token;
    }
}
