package com.seed.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 검증 실패 시 단일 필드의 에러 정보를 담는 DTO
 */
@Getter
@Builder
@AllArgsConstructor
public class ErrorDetail {

    /** 에러가 발생한 필드명 */
    private final String field;

    /** 사람이 읽을 수 있는 이유(메시지) */
    private final String reason;

    /** 거절된 값(디버깅/로그용, UI 표시는 상황에 따라) */
    private final Object rejected;
}
