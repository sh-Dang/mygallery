package com.sh.mygallery.board.service;

import com.sh.mygallery.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * controller의 요청을 받아 올바른 Repository로 요청을 전달해 주기위한 객체
 *
 * @author 이세형
 * @since 2025/11/30
 */
@Service
@RequiredArgsConstructor
public class BoardService {
    // 알맞은 repository객체 보유
    private final BoardRepository boardRepository;
}
