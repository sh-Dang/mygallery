package com.sh.mygallery.board.controller;

import com.sh.mygallery.board.service.BoardService;
import com.sh.mygallery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
/**
 * Boards관련된 Service를 찾아 명령하는 Controller
 *
 * @author 이세형
 * @since 2025/11/30
 */
@Controller
@RequiredArgsConstructor
public class BoardController {
    // 알맞은 service객체 보유
    private final BoardService boardService;
}
