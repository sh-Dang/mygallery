package com.sh.mygallery.exception;

/**
 * 프로젝트 내 전역적인 예외 처리를 해주는 Exception객체입니다.
 *
 * @author 이세형
 * @since 2025-12-04
 */

public class GlobalExceptionHandler extends RuntimeException {
    public GlobalExceptionHandler(String message) {
        super(message);
    }
}
