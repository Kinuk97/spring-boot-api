package com.kinuk.api.config.security;

import com.kinuk.api.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    /**
     * Spring Security 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(conf -> conf.configurationSource(corsConfigurationSource())) // cors 허용
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // form login 비활성화
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .authorizeHttpRequests(authorize -> {
                            authorize
                                    .requestMatchers("/v3/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Swagger Auth 제외 설정
                                    .requestMatchers("/api/user/signup", "/api/user/login").permitAll() // 회원가입, 로그인
                                    .anyRequest().authenticated(); // 이외의 요청은 Jwt 토큰 검증 필요
                        }
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Jwt 사용자 인증 필터
                .exceptionHandling(it -> it.authenticationEntryPoint(entryPoint)) // 사용자 인증에 실패한 경우 response 처리
        ;

        return http.build();
    }

    /**
     * cors 모두 허용
     */
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            config.addAllowedOriginPattern("*");

            return config;
        };
    }
}
