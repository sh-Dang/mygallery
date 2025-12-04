package com.sh.mygallery.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security의 설정을 담당하는 객체입니다.
 *
 * @author 이세형
 * @since 2025-12-04
 */
@Configuration // 스프링의 설정 클래스
@EnableWebSecurity // Spring Security활성화
@RequiredArgsConstructor // 의존성 주입용 어노테이션
public class SecurityConfig {

    /**
     * 비밀번호 암호화를 위한 인코더를 반환해주는 객체(Bean)
     *
     * @since 2025-12-04
     * @return BCrypt기반Encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security의 보안 필터 체인 설정
     *
     * @param http HTTP보안 설정 객체
     * @return SecurityFilterChain객체
     * @throws Exception 보안설정 처리중 발생가능한 예외
     */
    @Bean
    @Order(1) // 여러 개의 SecurityFilterChain이 있을 경우 우선순위 지정
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 설정된 보안 필터 체인을 최종 빌드하여 반환
        return http.build();
    }
}
