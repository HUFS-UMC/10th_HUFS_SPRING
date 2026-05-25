package com.springboot.umc10thlea.domain.member.exception.code;

import com.springboot.umc10thlea.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "MEMBER404_1",
            "유저 조회에 실패했습니다."),

    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,
            "MEMBER400_1",
            "이미 존재하는 이메일입니다."),

    LOGIN_FAILED(HttpStatus.UNAUTHORIZED,
            "MEMBER401_1",
            "이메일 또는 비밀번호가 올바르지 않습니다."),

    REQUIRED_TERM_NOT_AGREED(HttpStatus.BAD_REQUEST,
            "MEMBER400_2",
            "필수 약관에 동의해야 합니다."),

    FOOD_NOT_FOUND(HttpStatus.NOT_FOUND,
            "MEMBER404_2",
            "존재하지 않는 음식입니다."),

    TERM_NOT_FOUND(HttpStatus.NOT_FOUND,
            "MEMBER404_3",
            "존재하지 않는 약관입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
