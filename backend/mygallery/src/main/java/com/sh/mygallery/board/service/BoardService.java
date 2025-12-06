package com.sh.mygallery.board.service;

import com.sh.mygallery.board.domain.Board;
import com.sh.mygallery.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * controller의 요청을 받아 올바른 Repository로 요청을 전달해 주기위한 객체
 *
 * @author 이세형
 * @since 2025-11-30
 */
@Service
@RequiredArgsConstructor
public class BoardService {
    // 알맞은 repository객체 보유
    private final BoardRepository boardRepository;

    /**
     * 게시판의 모든 글을 불러오는 메서드
     *
     * @return 모든 게시글을 List의 형태로 반환
     */
    public List<Board> getAllBoards(){
        return boardRepository.findAll();
    }

    /**
     * 특정 사용자가 작성한 모든 게시글을 조회하는 메서드
     *
     * @param userId 작성자 ID
     * @return 사용자가 작성한 게시글 목록
     */
    public List<Board> getBoardsByUserId(Long userId) {
        return boardRepository.findByUserId(userId);
    }
}
