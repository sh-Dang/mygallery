package com.sh.mygallery.domain.board.service;

import com.sh.mygallery.domain.board.domain.Board;
import com.sh.mygallery.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
        return boardRepository.findByUserUserId(userId);
    }

    /**
     * 새로운 게시글을 작성하는 메서드
     *
     * @param board 작성할 게시글 정보
     * @return 저장된 게시글
     */
    public Board write(Board board){
        return boardRepository.save(board);
    }

    /**
     * 기존 게시글을 수정하는 메서드
     *
     * @param id 수정할 게시글 ID
     * @param board 수정할 내용
     * @return 수정된 게시글
     */
    @Transactional
    public Board update(Long id, Board board) {
        Board existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + id));
        existingBoard.setTitle(board.getTitle());
        existingBoard.setContent(board.getContent());

        return boardRepository.save(existingBoard);
    }

    /**
     * 게시글을 삭제하는 메서드
     *
     * @param id 삭제할 게시글 ID
     */
    @Transactional
    public void delete(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + id);
        }
        boardRepository.deleteById(id);
    }
}

