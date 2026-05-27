package com.example.umc10th.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT AccessToken 생성 · 이메일 추출 · 유효성 검증 · Claims 파싱을 담당합니다.
 *
 * <p>사용 라이브러리: io.jsonwebtoken:jjwt 0.12.x
 * <br>서명 알고리즘: HMAC-SHA256 (HS256) — 비밀키는 최소 32바이트(256비트)여야 합니다.
 */
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMs) {
        // 문자열 비밀키를 HMAC-SHA256 SecretKey로 변환
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * 이메일을 subject에 담은 AccessToken을 생성합니다.
     *
     * @param email 토큰 subject로 저장될 회원 이메일
     * @return 서명된 JWT 문자열
     */
    public String createAccessToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)          // 이메일을 subject claim에 저장
                .issuedAt(now)           // 발급 시각
                .expiration(expiry)      // 만료 시각
                .signWith(secretKey)     // HMAC-SHA256 자동 선택
                .compact();
    }

    /**
     * 토큰에서 이메일(subject)을 추출합니다.
     *
     * @param token Bearer 이후의 순수 JWT 문자열
     * @return subject에 담긴 이메일
     */
    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * 토큰의 서명·만료 여부를 검증합니다.
     *
     * @param token 검증할 JWT 문자열
     * @return 유효하면 true, 만료·위변조 등이면 false
     */
    public boolean isValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 토큰을 파싱하여 Claims를 반환합니다.
     * 서명 검증 및 만료 시각 검사를 동시에 수행합니다.
     *
     * @param token JWT 문자열
     * @return 파싱된 Claims 객체
     * @throws io.jsonwebtoken.JwtException 서명 불일치 또는 만료된 경우
     */
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)   // 서명 검증 키 설정
                .build()
                .parseSignedClaims(token)
                .getPayload();           // Claims 추출
    }
}
