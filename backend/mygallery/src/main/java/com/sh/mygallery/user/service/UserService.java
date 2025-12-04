package com.sh.mygallery.user.service;

import com.sh.mygallery.user.domain.User;
import com.sh.mygallery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

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
    /**
     * 유저의 회원가입을 담당하는 메서드
     *
     * @author 이세형
     * @since 2025-11-28
     */
    public Map<User,Long> register(){
        return  null;
    }

    /**
     * 유저의 로그인을 담당하는 메서드
     *
     * @author 이세형
     * @since 2025-11-28
     */
    public  Map<User,String> login(String username, String password){
        return  null;
    }
}
