package com.example.umc10th.global.exception;

import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponse<?>> handleMemberException(MemberException e) {
        return toErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(MissionException.class)
    public ResponseEntity<ApiResponse<?>> handleMissionException(MissionException e) {
        return toErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<ApiResponse<?>> handleReviewException(ReviewException e) {
        return toErrorResponse(e.getErrorCode());
    }

    // [7주차] @Valid 유효성 검증 실패 처리
    // result 내부에 Map<fieldName, errorMessage> 구조로 전체 오류 목록 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.onFailure(
                        GeneralErrorCode.BAD_REQUEST.getCode(),
                        "유효성 검사에 실패했습니다.",
                        errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("[COMMON500] Unhandled exception: {}", e.getMessage(), e);
        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.onFailure("COMMON500", "서버 오류가 발생했습니다.", null));
    }

    private ResponseEntity<ApiResponse<?>> toErrorResponse(BaseErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), null));
    }
}
