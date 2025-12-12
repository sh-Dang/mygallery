package com.sh.mygallery.domain.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * Users table에 매칭할 엔터티 선언
 *
 * @author 이세형
 * @since 2025/11/29
 */
@Entity // 데이터베이스에 존재하는 엔터티임을 명시(JPA)
@Table(name="users") // 테이블 명을 직접 입력해 매핑
@Getter
@Builder // 필드가 많아도 가독성 있게 객체 생성 가능(순서 의존성 제거, 선택적 값 설정에 유리) new 대용
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 기본 키(PK)로 사용되는 식별자(ID) 필드.
     * Id
     *  - 해당 필드를 엔티티의 기본 키(Primary Key)로 지정한다.
     * <p>
     * GeneratedValue(strategy = GenerationType.IDENTITY)
     *  - 기본 키 값을 데이터베이스가 자동으로 생성하도록 설정한다.
     *  - MySQL, MariaDB의 AUTO_INCREMENT 방식과 동일하게 동작한다.
     *  - INSERT 시점에 DB가 값을 생성하고, 생성된 값이 엔티티에 바로 반영된다.
     * @since 2025-11-29
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;

    // 유저 이름
    @Column(name="username")
    private String username;

    // 비밀번호
    @Column(name="password")
    private String password;

    // 유저 email
    @Column(name="email")
    private String email;

    // 유저 역할(user/admin 등)
    @Column(name="role")
    private String role;

    // softdelete를 위한 계정 상태 컬럼
    @Column(name="account_non_locked")
    private Boolean accountNonLocked;

    // 로그인타입(사이트/sns)
    @Column(name="login_type")
    private Boolean loginType;

    // 생성(가입)날짜
    @Column(name="created_at")
    private LocalDateTime createdAt;

    // 최종 수정 날짜
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
}
