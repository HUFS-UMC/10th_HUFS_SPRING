package com.springboot.umc10thlea.global.apiPayload.handler;

import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import com.springboot.umc10thlea.global.apiPayload.code.BaseErrorCode;
import com.springboot.umc10thlea.global.apiPayload.code.GeneralErrorCode;
import com.springboot.umc10thlea.global.apiPayload.exception.ProjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionAdvice {

    // 프로젝트 정의 예외 처리
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleProjectException(ProjectException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode));
    }

    // @Valid 검증 실패 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errors.putIfAbsent(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(GeneralErrorCode.BAD_REQUEST.getStatus())
                .body(new ApiResponse<>(
                        false,
                        GeneralErrorCode.BAD_REQUEST.getCode(),
                        GeneralErrorCode.BAD_REQUEST.getMessage(),
                        errors));
    }

    // 그 외 정의되지 않은 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "COMMON500", "서버 에러가 발생했습니다.", e.getMessage()));
    }
}
