package com.kinuk.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinuk.api.exception.ApiException;
import com.kinuk.api.service.UserService;
import com.kinuk.api.util.ApiResponse;
import com.kinuk.api.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ApiException apiException) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(apiException.getApiResponseCode().getHttpStatus().value());
            response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.fail(apiException.getApiResponseCode().getMessage())));
            response.getWriter().flush();
        }
    }
}
