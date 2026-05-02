package com.example.umc10th.domain.member.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "멤버를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
