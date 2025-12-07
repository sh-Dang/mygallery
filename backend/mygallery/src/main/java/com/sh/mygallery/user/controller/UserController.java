package com.sh.mygallery.user.controller;

import com.sh.mygallery.user.dto.UserDTO;
import com.sh.mygallery.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/user") // 유저로 들어가는 모든 요청은 '/user'로 시작되도록 매핑
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
     */
    @PostMapping("/login") // "/user/login"으로 들어오는 요청을 받아들임
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO.UserLoginRequest loginRequest){ // 요청 본문을 UserLoginRequest DTO로 바인딩 (JSON -> 객체)
        String token = userService.login( // UserService의 login 메서드를 호출하여 인증을 수행하고 JWT 토큰을 발급받음
                loginRequest.getUsername(), // DTO에서 username을 추출하여 전달 (이메일)
                loginRequest.getPassword()); // DTO에서 password(평문)를 추출하여 전달 — 절대 로그에 남기지 말 것
        Map<String, String> response = new HashMap<>(); // 응답으로 반환할 Map 객체 생성 (간단한 키-값 응답 용도)
        response.put("token", token); // 발급된 JWT 토큰을 "token" 키로 응답 바디에 추가
        return ResponseEntity.ok(response); // HTTP 200(OK)와 함께 Map을 JSON으로 변환해 응답
    }
}
