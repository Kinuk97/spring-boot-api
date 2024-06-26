package com.kinuk.api.config.security;

import com.kinuk.api.filter.JwtExceptionHandlerFilter;
import com.kinuk.api.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private JwtExceptionHandlerFilter jwtExceptionHandlerFilter;

    /**
     * Spring Security 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable) // cors 비활성화
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // form login 비활성화
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .authorizeHttpRequests(authorize -> {
                            authorize
                                    .requestMatchers("/v3/**", "/swagger-ui/**").permitAll() // Swagger Auth 제외 설정
                                    .requestMatchers("/api/user/signup", "/api/user/login").permitAll() // 회원가입, 로그인
                                    .anyRequest().authenticated(); // 이외의 요청은 Jwt 토큰 검증 필요
                        }
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Jwt 사용자 인증 필터
                .addFilterBefore(jwtExceptionHandlerFilter, JwtFilter.class) // Jwt 필터에서 throw 하는 Exception handler
                .exceptionHandling(it -> it.authenticationEntryPoint(entryPoint)) // 사용자 인증에 실패한 경우 response 처리
        ;

        return http.build();
    }

    /**
     * permit_url 은 security 설정 무시하도록 설정
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(
                        "/v3/**", "/swagger-ui/**",
                        "/api/user/signup", "/api/user/login"
                );
    }

    /**
     * Jwt 필터가 자동으로 servletFilter 에도 등록이 되어 ignore 했음에도 불구하고 filter 로 걸러지는 경우가 발생
     * 자동으로 jwtFilter 가 등록되지 않도록 설정
     */
    @Bean
    public FilterRegistrationBean<JwtFilter> registration(JwtFilter filter) {
        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

}
