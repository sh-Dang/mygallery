package com.sh.mygallery.domain.board.service;

import com.sh.mygallery.domain.board.domain.Board;
import com.sh.mygallery.domain.board.exception.NoTitleException;
import com.sh.mygallery.domain.board.exception.NotUserException;
import com.sh.mygallery.domain.board.repository.BoardRepository;
import com.sh.mygallery.domain.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

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
        return boardRepository.findByUserUserId(userId);
    }

    /**
     * 새로운 게시글을 작성하는 메서드
     *
     * @param board 작성할 게시글 정보
     * @return 저장된 게시글
     */
    public Board write(Board board){
        if (!StringUtils.hasText(board.getTitle())) {
            throw new NoTitleException("게시글의 제목은 필수입니다.");
        }
        if (board.getUser() == null) {
            throw new NotUserException("로그인하지 않은 사용자는 게시글을 작성할 수 없습니다.");
        }
        return boardRepository.save(board);
    }

    /**
     * 기존 게시글을 수정하는 메서드
     *
     * @param id 수정할 게시글 ID
     * @param board 수정할 내용
     * @param user 현재 로그인한 사용자
     * @return 수정된 게시글
     */
    @Transactional
    public Board update(Long id, Board board, User user) {
        if (!StringUtils.hasText(board.getTitle())) {
            throw new NoTitleException("게시글의 제목은 필수입니다.");
        }

        Board existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + id));

        if (!Objects.equals(existingBoard.getUser().getUserId(), user.getUserId())) {
            throw new NotUserException("게시글을 수정할 권한이 없습니다.");
        }

        existingBoard.setTitle(board.getTitle());
        existingBoard.setContent(board.getContent());

        return boardRepository.save(existingBoard);
    }

    /**
     * 게시글을 삭제하는 메서드
     *
     * @param id 삭제할 게시글 ID
     * @param user 현재 로그인한 사용자
     */
    @Transactional
    public void delete(Long id, User user) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + id));

        if (!Objects.equals(board.getUser().getUserId(), user.getUserId())) {
            throw new NotUserException("게시글을 삭제할 권한이 없습니다.");
        }
        boardRepository.deleteById(id);
    }
}

