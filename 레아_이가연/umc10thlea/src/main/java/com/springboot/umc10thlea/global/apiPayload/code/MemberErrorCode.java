package com.springboot.umc10thlea.global.apiPayload.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseSuccessCode {
    OK(HttpStatus.OK, "MEMBER400_1", "유저 조회에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}