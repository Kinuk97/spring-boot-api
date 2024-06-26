package com.kinuk.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class UserUpdateDto {

    @Data
    public static class Request {
        @Schema(description = "수정할 사용자 비밀번호", defaultValue = "123456")
        private String password;

        @Schema(description = "수정할 사용자 이름", defaultValue = "홍길동")
        private String name;
    }
}
