package com.sh.mygallery.domain.image.domain;

import com.sh.mygallery.domain.board.domain.Board;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글(Board)에 첨부되는 이미지 정보를 관리하는 JPA 엔티티.
 * <p>실제 파일 자체가 아닌, 파일 메타데이터(원본명, 저장명, 경로)를 저장한다.</p>
 *
 * @author 이세형
 * @since 2025-12-13
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    //이미지 식별자 (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 업로드 당시의 원본 파일명
    // 사용자에게 보여줄 용도
    @Column(nullable = false)
    private String originalFileName;

    // 서버에 실제로 저장된 파일명(중복 방지를 위한 UUID사용)
    @Column(nullable = false)
    private String storedFileName;

    // 파일이 저장된 경로
    @Column(nullable = false)
    private String filePath;

    // 이미지가 소속된 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    /**
     * 이미지 엔티티 생성자
     * 파일 업로드 시점에 필요한 메타데이터만 초기화한다.
     */
    @Builder
    public Image(String originalFileName, String storedFileName, String filePath) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.filePath = filePath;
    }
}
