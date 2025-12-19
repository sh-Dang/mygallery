package com.sh.mygallery.domain.user.controller;

import com.sh.mygallery.domain.refreshtoken.service.RefreshTokenService;
import com.sh.mygallery.domain.user.domain.User;
import com.sh.mygallery.domain.user.dto.UserDTO;
import com.sh.mygallery.domain.user.service.UserService;
import com.sh.mygallery.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    /**
     * 유저의 회원가입을 담당하는 메서드
     *
     * <p>클라이언트로부터 UserRegisterRequest(JSON)를 전달받아
     * userService.register(...)를 통해 실제 사용자 생성 비즈니스 로직을 수행,
     * 이후 정상적으로 생성된 User 엔티티를 HTTP 201(Created) 상태코드와 함께 반환</p>
     *
     * @param registerRequest 회원가입에 필요한 데이터로 생성된 DTO
     * @author 이세형
     * @since 2025-11-28
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO.UserRegisterRequest registerRequest){
        // front에서 받아온 사용자 데이터 생성 및 저장
        User registeredUser = userService.register(registerRequest);
        // 생성된 사용자 정보를 담아 201(CREATED)반환
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
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
        // Spring Security AuthenticationManager를 사용하여 사용자 인증
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // 인증 성공 후 토큰 생성
        String accessToken = jwtUtil.createAccessToken(loginRequest.getUsername()); // DTO에서 username을 추출하여 전달 (이메일)
        String refreshToken = refreshTokenService.issueRefreshToken(loginRequest.getUsername()); // DTO에서 password(평문)를 추출하여 전달 — 절대 로그에 남기지 말 것


        // refreshToken → HttpOnly 쿠키에 저장
        // 왜 Service가 아닌 Controller에서 쿠키에 저장하는가?
        // Cookie는 HTTP통신을 담당하는 레이어에서 처리해야 하는데,
        // 비즈니스 로직을 처리하는 Service에서 Cookie를 다룰경우 의존성이 높아져 MVC 패턴의 규칙성이 꺠진다고 볼 수 있기 떄문.
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)          // https 환경에서만 사용, 실서비스 필수
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

    /**
     * <p>시간이 만료된 accessToken이 존재하는 경우 refreshToken을 재검증하여
     * 자동으로 accessToken을 재발급 해 주는 메서드</p>
     *
     * @param request HttpServletRequest 객체. 클라이언트가 보낸 쿠키에서 Refresh Token을 추출하는 데 사용됨.
     * @return 새로운 Access Token을 포함한 Map 객체. ("accessToken" 키에 재발급된 토큰이 들어있음)
     * @since 2025-12-11
     */
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(HttpServletRequest request) {
        // 클라이언트 요청에 포함된 모든 쿠키를 가져옴
        Cookie[] cookies = request.getCookies();
        // 쿠키에서 refreshToken 값을 저장할 변수 초기화
        String refreshToken = null;

        // 쿠키가 존재할 경우에만 반복문 실행
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 쿠키 이름이 "refreshToken"인 경우를 찾음
                if ("refreshToken".equals(cookie.getName())) {
                    // refreshToken 값을 변수에 저장
                    refreshToken = cookie.getValue();
                    //찾은 경우 반복문 종료
                    break;
                }
            }
        }

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "RefreshToken을 찾을 수 없습니다."));
        }

        try {
            String newAccessToken = refreshTokenService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "유효하지 않은 토큰입니다."));
        }
    }
}

