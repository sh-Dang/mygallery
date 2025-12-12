package com.sh.mygallery.domain.user.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * social_accounts table에 매칭할 엔터티 선언
 *
 * @author 이세형
 * @since 2025-12-02
 */
@Entity
@Table(name="social_accounts")
public class SocialAccount {
    // social_accounts 기본키(PK) 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="social_account_id")
    private Long socialAccountId;
    // 소셜로그인 제공자(구글 카카오등)
    @Column(name="provider")
    private String provider;
    // provider에서 제공하는 id저장 컬럼
    @Column(name="provider_id")
    private String providerId;
    // 로그인하는 user(fk)
    @Column(name="user_id")
    private long userId;
    // 생성(가입)날짜
    @Column(name="created_at")
    private LocalDateTime createdAt;
}
