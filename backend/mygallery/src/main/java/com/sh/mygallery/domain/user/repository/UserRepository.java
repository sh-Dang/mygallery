package com.sh.mygallery.domain.user.repository;

import com.sh.mygallery.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 직접 Users Table에 접근하여 database를 다룰 객체
 *
 * @author 이세형
 * @since 2025-11-30
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * email 값을 기준으로 User 엔티티를 조회하는 메서드.
     *
     * <p>Spring Data JPA의 메서드 네이밍 규칙에 따라 자동으로
     * SELECT * FROM user WHERE email = ? 형태의 SQL을 실행</p>
     *
     * @param email 조회할 사용자의 이메일
     * @return Optional<User>
     *         - email이 존재하면 Optional 내부에 User 객체를 담아 반환
     *         - 존재하지 않으면 Optional.empty() 반환
     * @since 2025-12-07
     */
    Optional<User> findByEmail(String email);
}