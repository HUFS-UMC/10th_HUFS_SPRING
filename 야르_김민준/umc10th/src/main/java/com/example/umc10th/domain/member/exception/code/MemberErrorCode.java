package com.example.umc10th.domain.member.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4001", "해당 사용자를 찾을 수 없습니다."),
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4002", "해당 음식 카테고리를 찾을 수 없습니다."),
    TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4003", "해당 약관을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}