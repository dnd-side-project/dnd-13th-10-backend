package com.seed.global.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    // 공통 성공
    OK("S100", "요청이 성공적으로 처리되었습니다.", HttpStatus.OK),
    RESOURCE_CREATED("S101", "리소스가 성공적으로 생성되었습니다.", HttpStatus.CREATED),
    NO_CONTENT("S102", null, HttpStatus.NO_CONTENT), // 메시지 생략 가능

    // 회고 관련
    RETROSPECT_CREATED("S200", "회고 등록에 성공했습니다.", HttpStatus.CREATED),
    RETROSPECT_UPDATED("S201", "회고 수정에 성공했습니다.", HttpStatus.OK),
    RETROSPECT_DELETED("S202", "회고 삭제에 성공했습니다.", HttpStatus.NO_CONTENT),

    // 면접 일정 관련
    INTERVIEW_SCHEDULE_CREATED("S300", "면접 일정을 등록했습니다.", HttpStatus.CREATED),
    INTERVIEW_SCHEDULE_UPDATED("S301", "면접 일정을 수정했습니다.", HttpStatus.OK),
    INTERVIEW_SCHEDULE_DELETED("S302", "면접 일정을 삭제했습니다.", HttpStatus.NO_CONTENT);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}