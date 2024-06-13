package com.kinuk.api.util;

import com.kinuk.api.exception.ApiResponseCode;
import com.kinuk.api.exception.JwtTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${spring.jwt.key}}")
    private String SECRET_KEY;

    /**
     * Jwt 생성용 SecretKey 생성
     *
     * @return - SecretKey
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 토큰 생성 - 유효시간 24시간
     *
     * @param userId - 회원 ID
     * @return - Jwt 토큰
     */
    public String generateToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24시간
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * 토큰 Claims 추출 함수
     *
     * @param token - 발급된 토큰
     * @return - 토큰의 Claims
     */
    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * 토큰 검증 함수
     *
     * @param token - 발급된 토큰
     * @return - 검증 완료: true, 검증 실패: false
     */
    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            // 유효하지 않은 토큰
            throw new JwtTokenException(ApiResponseCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰
            throw new JwtTokenException(ApiResponseCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            // 지원하지 않는 토큰
            throw new JwtTokenException(ApiResponseCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            // claims 가 정상이 아닌 경우
            throw new JwtTokenException(ApiResponseCode.EMPTY_TOKEN);
        } catch (Exception e) {
            log.info("Jwt Token Exception", e);
            throw new JwtTokenException(ApiResponseCode.EXCEPTION_TOKEN);
        }
    }

    /**
     * 발급된 토큰의 정보 추출
     *
     * @param token - 발급된 토큰
     * @return - 토큰을 발급한 유저의 id
     */
    public String extractUserId(String token) {
        return extractClaims(token).getSubject();
    }
}
