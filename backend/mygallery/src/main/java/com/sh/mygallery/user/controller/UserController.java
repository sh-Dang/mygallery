package com.sh.mygallery.user.controller;

import com.sh.mygallery.user.dto.UserDTO;
import com.sh.mygallery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Users관련된 Service를 찾아 명령하는 Controller
 *
 * @author 이세형
 * @since 2025-11-28
 */
@RestController
@RequiredArgsConstructor //Bean 자동주입
@RequestMapping("/users") // 유저로 들어가는 모든 요청은 '/user'로 시작되도록 매핑
public class UserController {
    // 알맞은 service객체 보유
    private final UserService userService;
    /**
     * 유저의 회원가입을 담당하는 메서드
     *
     * @author 이세형
     * @since 2025-11-28
     */
    @PostMapping("/register")
    public void register(){
        userService.register();
    }

    /**
     * 유저의 로그인을 담당하는 메서드
     *
     * @author 이세형
     * @since 2025-11-28
     * @param loginRequest DTO로 매핑된 데이터
     */
    @PostMapping("/login") // "/user/login"으로 들어오는 요청을 받아들임
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO.UserLoginRequest loginRequest){ // 요청 본문을 UserLoginRequest DTO로 바인딩 (JSON -> 객체)
        Map<String, String> tokens = userService.login( // UserService의 login 메서드를 호출하여 인증을 수행하고 JWT 토큰을 발급받음
                loginRequest.getUsername(), // DTO에서 username을 추출하여 전달 (이메일)
                loginRequest.getPassword()); // DTO에서 password(평문)를 추출하여 전달 — 절대 로그에 남기지 말 것

        String accessToken = tokens.get("accessToken"); // 반환받은 토큰들 중 accessToken추출
        String refreshToken = tokens.get("refreshToken"); // 반환받은 토큰들 중 refreshToken 추출

        // refreshToken → HttpOnly 쿠키에 저장
        // 왜 Service가 아닌 Controller에서 쿠키에 저장하는가?
        // Cookie는 HTTP통신을 담당하는 레이어에서 처리해야 하는데,
        // 비즈니스 로직을 처리하는 Service에서 Cookie를 다룰경우 의존성이 높아져 MVC 패턴의 규칙성이 꺠진다고 볼 수 있기 떄문.
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)          // https 환경에서만 사용 ★ 실서비스 필수
                .path("/")
                .sameSite("None")      // cross-site 허용 (프론트 localhost 개발 시 필요)
                .maxAge(7 * 24 * 60 * 60) // 7일
                .build();

//        Map<String, String> response = new HashMap<>(); // 응답으로 반환할 Map 객체 생성 (간단한 키-값 응답 용도)
//        response.put("accessToken", accessToken); // 발급된 JWT 토큰을 "token" 키로 응답 바디에 추가
//        return ResponseEntity.ok(response); // HTTP 200(OK)와 함께 Map을 JSON으로 변환해 응답
        // accessToken -> body로 반환
        //
        return ResponseEntity.ok()
                // 응답 헤더에 refreshToken 쿠키를 추가 JS접근 방지
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                // accessToken은 일반적으로 JS가 Authorization 헤더에 직접 실어 사용해야 하기 때문에
                // 쿠키가 아닌 응답 body로 제공
                .body(Map.of("accessToken", accessToken));
    }
}
