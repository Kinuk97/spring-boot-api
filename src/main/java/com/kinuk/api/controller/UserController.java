package com.kinuk.api.controller;

import com.kinuk.api.dto.SignupDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/user/")
@RestController
public class UserController {

    /**
     * 회원가입 API
     *
     * @param request - 회원 정보
     * @return ApiResponse - 처리 결과
     */
    @PostMapping("signup")
    public ResponseEntity<Object> singup(@RequestBody @Valid SignupDto.Request request) {
        Map<String, String> data = new HashMap<>();
        data.put("test", "test");

        return ResponseEntity.ok(data);
    }

}
