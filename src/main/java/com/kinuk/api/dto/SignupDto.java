package com.kinuk.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class SignupDto {

    @Data
    public static class Request {

        @NotBlank
        @Schema(description = "사용자 아이디", defaultValue = "kw68")
        private String userId;

        @NotBlank
        @Schema(description = "사용자 비밀번호", defaultValue = "123456")
        private String password;

        @NotBlank
        @Schema(description = "사용자 이름", defaultValue = "관우")
        private String name;
    }

}
