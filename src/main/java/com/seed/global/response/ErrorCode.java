package com.seed.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 요청 파라미터 / 검증 오류
    ERROR("E100", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELD("E101", "필수 입력 항목이 누락되었습니다.", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT("E102", "입력 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER("E103", "유효성 검증 실패", HttpStatus.BAD_REQUEST),

    // 인증 / 인가 오류
    UNAUTHORIZED("E200", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("E201", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    METHOD_NOT_ALLOWED("E202", "허용되지 않은 메서드입니다", HttpStatus.METHOD_NOT_ALLOWED),
    REFRESH_TOKEN_REQUIRED("E203", "토큰 재발급을 위해선 Refresh Token이 필요합니다.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_NOT_VALID("E204", "Refresh Token이 유효하지 않습니다", HttpStatus.BAD_REQUEST),

    // 데이터 관련
    NOT_FOUND("E300", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_RESOURCE("E301", "이미 존재하는 리소스입니다.", HttpStatus.CONFLICT),

    // 서버 오류
    SERVER_ERROR("E500", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 일정 관련
    SCHEDULE_NOT_FOUND("E401", "해당하는 일정을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}