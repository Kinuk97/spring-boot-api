package com.kinuk.api.filter;

import com.kinuk.api.exception.ApiResponseCode;
import com.kinuk.api.service.UserService;
import com.kinuk.api.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String jwtToken = authorization.substring(7);

            // 헤더에 토큰이 있고 검증 결과 문제가 없다면
            ApiResponseCode jwtResult = jwtUtil.validateToken(jwtToken);
            if (jwtResult == null) {
                String userId = jwtUtil.extractUserId(jwtToken);

                boolean check = userService.checkUserId(userId);

                if (check) {
                    // 인증 성공 후 처리
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userId, "", List.of());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    log.info("Token does not validate [{}]", request.getServletPath());
                    request.setAttribute("jwt_result", ApiResponseCode.EXCEPTION_TOKEN.getMessage());
                }
            } else {
                request.setAttribute("jwt_result", jwtResult.getMessage());
            }
        } else {
            request.setAttribute("jwt_result", ApiResponseCode.NO_TOKEN.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
