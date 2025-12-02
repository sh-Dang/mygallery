package com.sh.mygallery.board.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    // 게시판 제목
    @Column(name="title")
    private String title;
    // 게시글 내용
    @Column(name="content")
    private String content;
    // 생성날짜
    @Column(name="created_at")
    private LocalDateTime createdAt;
    // 최종 수정날짜
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    // 작성자(fk)
    @Column(name="user_id")
    private Long userId;
    // 조회수
    @Column(name="view_count")
    private int viewCount;

}
