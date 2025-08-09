package com.seed.global.exception;

import com.seed.global.response.ApiResponse;
import com.seed.global.response.ErrorCode;
import com.seed.global.response.ErrorDetail;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE) // 다른 Advice 들보다 먼저 실행되도록 우선순위를 최상위로 올리는 설정
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /** 비즈니스 규칙 위반 */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("[BusinessException] code={}, msg={}", errorCode.getCode(), e.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.error(errorCode, e.getMessage()));
    }

    /** 허용되지 않은 HTTP 메서드 */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
        log.warn("[MethodNotAllowed] {}", e.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.error(errorCode));
    }

    /** @Valid 또는 @Validated 객체 바인딩 시 필드 검증 실패(주로 @RequestBody DTO) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ErrorDetail(fe.getField(), fe.getDefaultMessage(), fe.getRejectedValue()))
                .toList();
        Map<String, Object> payload = Map.of("errors", errors);
        ErrorCode ec = ErrorCode.INVALID_PARAMETER;
        return ResponseEntity.status(ec.getHttpStatus())
                .body(ApiResponse.error(ec, "유효성 검증 실패", payload));
    }

    /** 폼 데이터(@ModelAttribute 등) 바인딩 시 필드 검증 실패 */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleBind(BindException ex) {
        List<ErrorDetail> errors = ex.getFieldErrors().stream()
                .map(fe -> new ErrorDetail(fe.getField(), fe.getDefaultMessage(), fe.getRejectedValue()))
                .toList();
        Map<String, Object> payload = Map.of("errors", errors);
        ErrorCode ec = ErrorCode.INVALID_PARAMETER;
        return ResponseEntity.status(ec.getHttpStatus())
                .body(ApiResponse.error(ec, "유효성 검증 실패", payload));
    }

    /** 단일 파라미터(@RequestParam, @PathVariable 등) 제약조건 위반 시 발생 */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleConstraintViolation(ConstraintViolationException ex) {
        List<ErrorDetail> errors = ex.getConstraintViolations().stream()
                .map(v -> new ErrorDetail(
                        // ex: "createUser.id" → 마지막 토큰만 필드로
                        extractField(v.getPropertyPath().toString()),
                        v.getMessage(),
                        v.getInvalidValue()
                )).toList();
        Map<String, Object> payload = Map.of("errors", errors);
        ErrorCode ec = ErrorCode.INVALID_PARAMETER;
        return ResponseEntity.status(ec.getHttpStatus())
                .body(ApiResponse.error(ec, "유효성 검증 실패", payload));
    }

    /** 최종 안전망 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnknown(Exception e) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        log.error("[Unhandled] {}", e.getMessage(), e);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.error(errorCode));
    }

    /**
     * TODO : 뭔가 더 추가를 한다면 파일 업로드 예외(MaxUploadSizeExceededException)?
     *         혹은 Json 파싱에러(HttpMessageNotReadableException) 관련?
     */

    private String extractField(String path) {
        int idx = path.lastIndexOf('.');
        return (idx >= 0) ? path.substring(idx + 1) : path;
    }
}
