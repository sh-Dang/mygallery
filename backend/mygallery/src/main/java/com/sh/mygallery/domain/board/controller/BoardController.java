package com.sh.mygallery.domain.board.controller;

import com.sh.mygallery.domain.board.domain.Board;
import com.sh.mygallery.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Boards관련된 Service를 찾아 명령하는 Controller
 *
 * @author 이세형
 * @since 2025-11-30
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    // 알맞은 service객체 보유
    private final BoardService boardService;

    /**
     * 게시글 생성(Create)을 처리하는 메서드
     * 클라이언트가 전달한 게시글 데이터를 기반으로 새로운 Board 객체를 저장
     *
     * @return 생성된 Board 정보를 ResponseEntity로 래핑하여 반환
     */
    @PostMapping
    public ResponseEntity<Board> write(@RequestBody Board board) {
        Board createdBoard = boardService.write(board);
        return ResponseEntity.ok(createdBoard);
    }

    /**
     * 전체 게시글 목록을 조회하는 메서드
     * 클라이언트 요청을 받아 BoardService에서 조회한 게시글 리스트를 반환
     *
     * @return 조회된 게시글 리스트(List<Board>)를 ResponseEntity로 감싸서 반환
     */
    @GetMapping
    public ResponseEntity<List<Board>> getBoards(){
        List<Board> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    /**
     * 특정 사용자의 게시글 목록을 조회하는 메서드
     *
     * @param userId 사용자 ID
     * @return 해당 사용자가 작성한 게시글 리스트
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Board>> getBoardsByUserId(@PathVariable Long userId) {
        List<Board> boards = boardService.getBoardsByUserId(userId);
        return ResponseEntity.ok(boards);
    }

    /**
     * 게시글 수정(Update)을 처리하는 메서드
     * 전달된 데이터로 기존 게시글을 갱신
     *
     * @return 수정된 Board 정보를 ResponseEntity로 래핑하여 반환
     */
    @PutMapping("/{id}")
    public ResponseEntity<Board> update(@PathVariable Long id, @RequestBody Board board){
        Board updatedBoard = boardService.update(id, board);
        return ResponseEntity.ok(updatedBoard);
    }

    /**
     * 게시글 삭제(Delete)을 처리하는 메서드
     * 특정 게시글을 식별하여 시스템에서 제거
     *
     * @return 삭제 성공 여부를 포함한 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
