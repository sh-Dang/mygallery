package com.sh.mygallery.service;

import com.sh.mygallery.domain.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    /**
     * 유저의 회원가입을 담당하는 메서드
     *
     * @author 이세형
     * @since 2025/11/28
     */
    public Map<User,Long> register(){
        return  null;
    }

    /**
     * 유저의 로그인을 담당하는 메서드
     *
     * @author 이세형
     * @since 2025/11/28
     */
    public  Map<User,String> login(String username,String password){
        return  null;
    }
}
