package com.example.umc10th.domain.member.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public MemberException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
