package com.seed.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 필드는 응답에서 제외
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private String code;            // 비즈니스 코드
    private String message;         // 사용자 메시지
    private T data;                 // 응답 데이터

    public static <T> ApiResponse<T> success(T data) {
        return success(SuccessCode.OK, data);
    }

    public static ApiResponse<Void> success() {
        return success(SuccessCode.OK, null);
    }

    public static <T> ApiResponse<T> success(SuccessCode sc, T data) {
        return ApiResponse.<T>builder()
                .code(sc.getCode())
                .message(sc.getMessage())
                .data(data)
                .build();
    }

    public static ApiResponse<Void> error(ErrorCode ec) {
        return error(ec, ec.getMessage());
    }

    public static ApiResponse<Void> error(ErrorCode ec, String overrideMessage) {
        return ApiResponse.<Void>builder()
                .code(ec.getCode())
                .message(overrideMessage)
                .build();
    }

    public static <T> ApiResponse<T> error(ErrorCode ec, T data) {
        return ApiResponse.<T>builder()
                .code(ec.getCode())
                .message(ec.getMessage())
                .data(data)
                .build();
    }

    /** 에러에 추가 payload(예: errors 배열) 실을 때 사용 */
    public static <T> ApiResponse<T> error(ErrorCode ec, String overrideMessage, T data) {
        return ApiResponse.<T>builder()
                .code(ec.getCode())
                .message(overrideMessage)
                .data(data)
                .build();
    }
}