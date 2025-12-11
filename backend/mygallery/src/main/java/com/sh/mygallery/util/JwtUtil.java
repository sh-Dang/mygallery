package com.sh.mygallery.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * JWT 처리를 위한 유틸리티 클래스
 * 토큰생성, 토큰검증 등의 기능을 제공
 * 제공하는 기능은 추 후 추가
 *
 * @author 이세형
 * @since 2025-12-04
 */
@Component
@RequiredArgsConstructor
public class JwtUtil {

    // 알고리즘에 사용할 SecretKey (application.properties에서 주입)
    private final SecretKey secretKey;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 생성자에서 application.properties의 비밀키 문자열을 받아서
     * JWT 서명에 사용 가능한 SecretKey 객체로 변환
     *
     * @param secretKey application.properties의 jwt.signature.secretkey 값
     */
    public JwtUtil(@Value("${jwt.signature.secretkey}") String secretKey, RedisTemplate<String, String> redisTemplate) {
        // Keys.hmacShaKeyFor(): 문자열을 바이트 배열로 변환하여 HMAC-SHA 알고리즘용 SecretKey 객체 생성
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.redisTemplate = redisTemplate;
    }

    // accessToken을 생성하는 메서드
    public String createAccessToken(String subject){
        String accessToken = Jwts.builder() // 토큰을 만들기 위한 빌더 객체 생성
                .setSubject(subject) // 토큰의 주체(subject)를 설정(보통 사용자 ID나 이름)
                .setIssuedAt(new Date()) // 토큰 발행 시간을 현재 시간으로 설정
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 토큰 만료시간을 30분으로 설정
                .signWith(secretKey) // secretKey로 토큰에 서명 (토큰의 위변조 방지)
                .compact(); // 최종: JWT 문자열로 압축하여 생성
        return accessToken;
    }

    // accessToken 검증로직을 담고있는 메서드
    public Boolean validateAccessToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // refreshToken을 생성하는 메서드
    public String createRefreshToken(String subject){
        String refreshToken = Jwts.builder() // 토큰을 만들기 위한 빌더 객체 생성
                .setSubject(subject) // 사용자 식별용 (또는 UUID 사용 가능)
                .setId(UUID.randomUUID().toString()) // 토큰 고유 ID (jti) - DB에 값을 저장하여 탈취를 방지할 수 있음
                .setIssuedAt(new Date()) // 토큰 발행 시간을 현재 시간으로 설정
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 토큰 만료시간을 7일로 설정
                .signWith(secretKey) // secretKey로 토큰에 서명 (토큰의 위변조 방지)
                .compact(); // 최종: JWT 문자열로 압축하여 생성
        return refreshToken;
    }

    // refreshToken 검증로직을 담고있는 메서드
    public Boolean validateRefreshToken(String token){
        try {
            // 1. 토큰 파싱
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            String email = claims.getSubject();

            // 2. Redis에서 저장된 Refresh Token 조회
            String storedToken = redisTemplate.opsForValue().get(email);

            // 3. 토큰 비교
            return token.equals(storedToken);
        } catch (Exception e) {
            return false;
        }
    }

    // refreshToken으로부터 email을 추출하는 메서드
    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
