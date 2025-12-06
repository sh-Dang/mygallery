package com.sh.mygallery.user.repository;

import com.sh.mygallery.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 직접 Users Table에 접근하여 database를 다룰 객체
 *
 * @author 이세형
 * @since 2025-11-30
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}