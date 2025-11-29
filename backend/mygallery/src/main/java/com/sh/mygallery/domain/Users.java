package com.sh.mygallery.domain;

import jakarta.persistence.*;


/**
 * Users table에 매칭할 엔터티 선언
 *
 * @author 이세형
 * @since 2025/11/29
 */
@Entity
public class Users {
    /**
     * 기본 키(PK)로 사용되는 식별자(ID) 필드.
     * Id
     *  - 해당 필드를 엔티티의 기본 키(Primary Key)로 지정한다.
     * <p>
     * GeneratedValue(strategy = GenerationType.IDENTITY)
     *  - 기본 키 값을 데이터베이스가 자동으로 생성하도록 설정한다.
     *  - MySQL, MariaDB의 AUTO_INCREMENT 방식과 동일하게 동작한다.
     *  - INSERT 시점에 DB가 값을 생성하고, 생성된 값이 엔티티에 바로 반영된다.
     * <p>
     * ✔ 주의사항:
     *  - IDENTITY 전략은 기본 키를 DB에 의존하므로,
     *    대량 배치 INSERT 성능에는 불리할 수 있다.
     *  - 대부분의 웹 서비스 CRUD 환경에서는 가장 안정적이고 많이 사용된다.
     * @since 2025/11/29
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;


}
