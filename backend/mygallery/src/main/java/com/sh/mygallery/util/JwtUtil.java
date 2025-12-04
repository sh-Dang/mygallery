package com.sh.mygallery.util;

import org.springframework.stereotype.Component;

/**
 * JWT 처리를 위한 유틸리티 클래스 입니다.
 * 토큰생성, 토큰검증 등의 기능을 제공합니다.
 * 제공하는 기능은 추 후 추가될 수 있습니다.
 *
 * @author 이세형
 * @since 2025-12-04
 */
@Component
public class JwtUtil {

    // accessToken을 생성하는 메서드
    private String createAccessToken(){
        final String accessToken = null;
        return accessToken;
    }

    // accessToken 검증로직을 담고있는 메서드
    private Boolean validateAccessToken(String token){
        // 값을 최초에 false로 초기화 유효하지 않은 정보에 대한 인증방지
        boolean isAccessTokenAvailable = false;
        return isAccessTokenAvailable;
    }

    // refreshToken을 생성하는 메서드
    private String createRefreshToken(){
        final String refreshToken = null;
        return refreshToken;
    }

    // refreshToken 검증로직을 담고있는 메서드
    private Boolean validateRefreshToken(String token){
        // 값을 최초에 false로 초기화 유효하지 않은 정보에 대한 인증방지
        boolean isRefreshTokenAvailable = false;
        return isRefreshTokenAvailable;
    }

}
