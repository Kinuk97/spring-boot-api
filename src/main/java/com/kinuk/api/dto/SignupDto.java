package com.kinuk.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class SignupDto {

    @Data
    public static class Request {

        @NotBlank
        @Schema(description = "사용자 아이디", example = "kw68")
        private String userId;

        @NotBlank
        @Schema(description = "사용자 비밀번호", example = "123456")
        private String password;

        @NotBlank
        @Schema(description = "사용자 이름", example = "관우")
        private String name;
    }

}
