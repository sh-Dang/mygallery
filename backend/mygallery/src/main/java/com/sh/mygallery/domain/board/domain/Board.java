package com.sh.mygallery.domain.board.domain;

import com.sh.mygallery.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * boards table에 매칭할 엔터티 선언
 *
 * @author 이세형
 * @since 2025-11-30
 */
@Entity
@Table(name="boards")
@Getter
public class Board {
    // boards 기본키(PK) 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long boardId;

    // 게시판 제목
    @Setter
    @Column(name="title")
    private String title;

    // 게시글 내용
    @Setter
    @Column(name="content")
    private String content;

    // 생성날짜
    @Column(name="created_at")
    private LocalDateTime createdAt;

    // 최종 수정날짜
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // 작성자(fk)
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩으로 설정
    @JoinColumn(name = "user_id") // 외래키 컬럼명 지정
    private User user;

    // 조회수
    @Column(name="view_count")
    private int viewCount;
}
