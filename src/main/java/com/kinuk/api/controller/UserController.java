package com.kinuk.api.controller;

import com.kinuk.api.dto.SignupDto;
import com.kinuk.api.service.UserService;
import com.kinuk.api.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user/")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 회원가입 API
     *
     * @param request - 회원 정보
     * @return ApiResponse - 처리 결과
     */
    @PostMapping("signup")
    public ApiResponse<?> singup(@RequestBody @Valid SignupDto.Request request) {
        userService.signup(request);

        return ApiResponse.ok();
    }

}
