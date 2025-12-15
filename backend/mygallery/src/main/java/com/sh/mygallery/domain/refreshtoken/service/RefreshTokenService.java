package com.sh.mygallery.domain.refreshtoken.service;

import com.sh.mygallery.domain.refreshtoken.entity.RefreshToken;
import com.sh.mygallery.domain.refreshtoken.repository.RefreshTokenRepository;
import com.sh.mygallery.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * refreshToken 발급등의 로직을 담고있는 Service Component
 *
 * @author 이세형
 * @since 2025-12-12
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    /**
     * Refresh Token을 발급하고 Redis에 저장합니다.
     *
     * @param userId 사용자 ID (email)
     * @return 생성된 Refresh Token 문자열
     */
    @Transactional
    public String issueRefreshToken(String userId) {
        String token = jwtUtil.createRefreshToken(userId);
        RefreshToken refreshToken = new RefreshToken(userId, token);
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    /**
     * 제공된 Refresh Token으로 새로운 Access Token을 발급합니다.
     *
     * @param token 검증할 Refresh Token
     * @return 새로 발급된 Access Token
     * @throws IllegalArgumentException Refresh Token이 유효하지 않거나 저장소에 없을 경우
     */
    @Transactional(readOnly = true)
    public String refreshAccessToken(String token) {
        // 1. JWT 유효성(서명, 만료) 검사 및 사용자 정보 추출
        String userId = jwtUtil.getEmailFromToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("Invalid or expired refresh token.");
        }

        // 2. 저장소에서 토큰 확인
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not found. It may have been revoked."));

        // 3. 새로운 Access Token 생성
        return jwtUtil.createAccessToken(userId);
    }

    /**
     * Refresh Token을 삭제합니다. (로그아웃 시 사용)
     *
     * @param userId 사용자 ID
     */
    @Transactional
    public void deleteRefreshTokenByUserId(String userId) {
        refreshTokenRepository.deleteById(userId);
    }
}