package com.sh.mygallery.conroller;

import com.sh.mygallery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor //Bean 자동주입
public class UserController {

    private final UserService userService;
    /**
     * 유저의 회원가입을 담당하는 메서드
     *
     * @author 이세형
     * @since 2025/11/28
     */
    @PostMapping
    public void register(){
        userService.register();
    }

    /**
     * 유저의 로그인을 담당하는 메서드
     *
     * @author 이세형
     * @since 2025/11/28
     */
    @PostMapping
    public void login(){
        userService.login(null,null);
    }
}
