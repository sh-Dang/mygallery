package com.sh.mygallery.board.repository;

import com.sh.mygallery.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 직접 Boards Table에 접근하여 database를 다룰 객체
 *
 * @author 이세형
 * @since 2025-11-30
 */
@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

}
