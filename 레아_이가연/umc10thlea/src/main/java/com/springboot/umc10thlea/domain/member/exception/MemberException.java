package com.springboot.umc10thlea.domain.member.exception;

import com.springboot.umc10thlea.global.apiPayload.code.BaseErrorCode;
import com.springboot.umc10thlea.global.apiPayload.exception.ProjectException;

public class MemberException extends ProjectException {
    public MemberException(BaseErrorCode code) {
        super(code);
    }
}
