package com.sh.mygallery.domain.board.repository;

import com.sh.mygallery.domain.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 직접 Boards Table에 접근하여 database를 다룰 객체
 *
 * @author 이세형
 * @since 2025-11-30
 */
@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    // 특정 사용자(userId)가 작성한 모든 게시글 조회하는 메서드
    List<Board> findByUserUserId(Long userId);
}
