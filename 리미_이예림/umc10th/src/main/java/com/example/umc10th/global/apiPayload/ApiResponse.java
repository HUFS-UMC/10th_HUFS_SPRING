package com.example.umc10th.global.apiPayload;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    private final T result;

    // 생성자
    private ApiResponse(Boolean isSuccess, String code, String message, T result) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    // ✅ 성공 응답 생성 메서드
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode successCode, T result) {
        return new ApiResponse<>(
                true,
                successCode.getCode(),
                successCode.getMessage(),
                result
        );
    }

    // ❌ 실패 응답 생성 메서드
    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, T result) {
        return new ApiResponse<>(
                false,
                errorCode.getCode(),
                errorCode.getMessage(),
                result
        );
    }
}
