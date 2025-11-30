package com.sh.mygallery.board.domain;

import jakarta.persistence.*;

/**
 * boards table에 매칭할 엔터티 선언
 *
 * @author 이세형
 * @since 2025/11/30
 */
@Entity
@Table(name="boards")
public class Board {
    // boards 기본키(PK) 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long boardId;

}
