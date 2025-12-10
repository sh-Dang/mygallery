package com.sh.mygallery.user.service;

import com.sh.mygallery.user.domain.User;
import com.sh.mygallery.user.dto.UserDTO;
import com.sh.mygallery.user.exception.UserException;
import com.sh.mygallery.user.repository.UserRepository;
import com.sh.mygallery.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * controller의 요청을 받아 올바른 Repository로 요청을 전달해 주기위한 객체
 *
 * @author 이세형
 * @since 2025-11-28
 */
@Service
@RequiredArgsConstructor
public class UserService {
    // 알맞은 repository객체 보유
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    /**
     * 유저의 회원가입을 담당하는 메서드
     *
     * @author 이세형
     * @since 2025-11-28
     */
    public User register(UserDTO.UserRegisterRequest userRegisterRequest){
        // 이메일 중복 확인
        Optional<User> existingUser = userRepository.findByEmail(userRegisterRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new UserException("이미 가입되어 있는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

        // User 객체 생성 및 저장
        User newUser = User.builder()
                .username(userRegisterRequest.getUsername())
                .password(encodedPassword)
                .email(userRegisterRequest.getEmail())
                .role("ROLE_USER") // 기본 역할 설정
                .accountNonLocked(true) // 계정 잠금 여부 (기본값 true)
                .loginType(false) // 사이트 로그인 (false: 사이트, true: SNS)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(newUser);
    }

    /**
     * 유저의 로그인을 담당하는 메서드
     *
     * <p>전달받은 username으로 DB에서 사용자 정보를 조회한 뒤,
     * 입력한 비밀번호가 저장된 해시와 일치하는지 검증 후
     * 검증에 성공하면 해당 사용자를 위한 JWT Access Token을 생성하여 반환한다.</p>
     *
     * @param email 로그인 시도 중인 사용자의 식별자(이메일)
     * @param password 클라이언트가 전달한 비밀번호(문자 그대로)
     * @return 생성된 JWT Access Token 문자열
     * @throws UsernameNotFoundException username에 해당하는 사용자가 없을 경우
     * @throws BadCredentialsException 비밀번호가 저장된 해시와 일치하지 않을 경우
     * @author 이세형
     * @since 2025-12-07
     */
    public Map<String,String> login(String email, String password) {
        // email을 기준으로 DB에서 User 엔티티 탐색.
        // 값이 없으면 UserNotFoundException을 발생시켜 로그인 실패 처리.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

        // 입력된 password(평문)와 DB에 저장된 암호화된 비밀번호가 일치하는지 검증.
        // matches()는 BCryptPasswordEncoder에 의해 암호 비교를 수행.
        // 일치하지 않으면 BadCredentialsException을 던져 인증 실패 처리.
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 모든 검증이 통과된 경우, username을 기반으로 JWT Access Token 생성.
        // 이 토큰은 이후 인증된 사용자임을 증명하는 데 사용됨.
//        return jwtUtil.createAccessToken(email);
        String accessToken = jwtUtil.createAccessToken(email);
        String refreshToke = jwtUtil.createRefreshToken(email);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToke
        );
    }
}
