package com.sh.mygallery.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * User의 민감정보를 제외한 데이터를 다룰때 사용할 DTO
 *
 * @author 이세형
 * @since 2025-11-30
 */
public class UserDTO {

    @Getter
    public static class UserRegisterRequest{
        private String username;
        private String password;
        private String email;
    }

    // UserDTO는 직관적이지 않은 클래스명이므로
    // 내부 클래스를 통해 각 요청/응답 DTO를 분리하여 사용하는 방식을 택함
    @Getter
    @Setter
    public static class UserLoginRequest {
        /**
         * 로그인에 사용되는 사용자 식별자(유저명)
         *
         * <p> 지금의 경우 email을 사용(username에 email 정보를 담아 인증)
         * 인증 필터에서 로그인 시도 시 Authentication 객체 생성에 사용</p>
         */
        private String username;

        /**
         * 로그인 비밀번호.
         *
         * <p>클라이언트로부터 전달되는 원문(password raw value)이며,
         * Spring Security 인증 과정에서 PasswordEncoder를 통해 검증
         * 서버 내부에서는 절대 평문으로 저장하거나 로그에 출력하지 않는다.</p>
         */
        private String password;
    }
}
