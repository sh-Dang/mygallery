package com.sh.mygallery.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 들어오는 모든 요청을 가로채서 JWT 검증을 가장먼저 실시하는 Spring Security 필터입니다.
 *
 * @author 이세형
 * @since 2025-12-04
 * */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    /**
     * HTTP 요청을 가로채 JWT를 검증하고 SecurityContextHolder를 업데이트 해주는 메서드 입니다.
     *
     * @param request 클라이언트가 보낸 요청
     * @param response 클라이언트에게 보내는 응답객체
     * @param filterChain 필터체인
     * @throws ServletException 서블릿 처리 관련 예외
     * @throws IOException I/O 처리 중 발생하는 예외
     * @since 2025-12-04
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}
