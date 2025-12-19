package com.sh.mygallery.config;

import com.sh.mygallery.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

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

    private final JwtRequestFilter jwtRequestFilter;
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
     * Spring Security가 내부적으로 생성한 AuthenticationManager를
     * 애플리케이션에서 주입받아 사용할 수 있도록 Bean으로 등록한다.
     *
     * @param authenticationConfiguration Spring Security 인증 설정 정보를 담고 있는 객체
     * @return Security 설정을 기반으로 생성된 AuthenticationManager
     * @throws Exception AuthenticationManager 생성 과정에서 예외가 발생할 수 있음
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * Spring Security의 보안 필터 체인 설정
     *
     * @param http HTTP보안 설정 객체
     * @return SecurityFilterChain객체
     * @throws Exception 보안설정 처리중 발생가능한 예외
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .securityMatcher("/user/**") //이 SecurityFilterChain이 "/user/**" 경로에만 적용되도록 매칭 설정
                // CSRF는 브라우저 자동 쿠키 전송을 악용한 공격을 막는 기능이므로
                // JWT는 쿠키가 아닌 Authorization 헤더를 사용하므로 CSRF 검증이 불필요하여 disable해야 함
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    // "CorsConfig config = "과 완전 같은코드
                    // new CorsConfiguration() 같이 생성하는 타입이 명확하면 var가 편함
                    var config = new org.springframework.web.cors.CorsConfiguration(); // CorsConfiguration 객체 생성
                    config.setAllowedOrigins(List.of("http://localhost:5173")); // 5173으로 부터 들어오는 요청을 허용
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")); // 모든 형태의 메서드를 허용
                    config.setAllowCredentials(true); // 자격증명(쿠키, 인증헤더 등)을 허용할지 여부 설정
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // 허용할 요청 헤더 목록 설정
                    config.setExposedHeaders(List.of("Set-Cookie")); // 브라우저가 접근 가능한 응답 헤더 목록 설정 (Set-Cookie를 노출)
                    return config; // 구성한 CorsConfiguration을 반환
                }))
                // URL 권한 규칙
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/user/login", "/user/register", "/user/check-email").permitAll() // 누구나 접근 가능
                        .requestMatchers("/user/mypage").authenticated() // JWT 필요
                        .anyRequest().permitAll()
                )

                // 세션 사용 안 함 (JWT 사용 시 필수)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // UsernamePasswordAuthenticationFilter 앞에 JWT 필터 추가
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
