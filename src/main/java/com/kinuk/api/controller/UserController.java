package com.kinuk.api.controller;

import com.kinuk.api.dto.LoginDto;
import com.kinuk.api.dto.SignupDto;
import com.kinuk.api.dto.UserUpdateDto;
import com.kinuk.api.service.UserService;
import com.kinuk.api.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원관리")
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
    @Operation(summary = "회원가입")
    @PostMapping("signup")
    public ApiResponse<?> singup(@RequestBody @Valid SignupDto.Request request) {
        userService.signup(request);

        return ApiResponse.ok();
    }

    /**
     * 로그인 API
     *
     * @param request - 로그인 정보
     * @return ApiResponse - 토큰이 포함된 처리 결과
     */
    @Operation(summary = "로그인")
    @PostMapping("login")
    public ApiResponse<LoginDto.Response> login(@RequestBody @Valid LoginDto.Request request) {
        String token = userService.login(request);

        return ApiResponse.ok(LoginDto.Response.builder().token(token).build());
    }

    /**
     * 회원정보 수정 API
     *
     * @param request - 수정 회원 정보
     * @return ApiResponse - 수정 결과
     */
    @Operation(summary = "정보 수정")
    @PostMapping("update")
    public ApiResponse<?> update(@RequestBody @Valid UserUpdateDto.Request request) {
        userService.update(request);

        return ApiResponse.ok();
    }

}
