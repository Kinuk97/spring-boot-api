package com.kinuk.api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinuk.api.exception.ApiResponseCode;
import com.kinuk.api.util.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Authentication 인증 없이 API 요청한 경우 응답 처리
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ApiResponseCode.INVALID_TOKEN.getHttpStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.fail(ApiResponseCode.INVALID_TOKEN.getMessage())));
        response.getWriter().flush();
    }
}
