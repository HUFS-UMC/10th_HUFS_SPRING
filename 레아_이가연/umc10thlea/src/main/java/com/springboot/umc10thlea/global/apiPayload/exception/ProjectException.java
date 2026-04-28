package com.springboot.umc10thlea.global.apiPayload.exception;

import com.springboot.umc10thlea.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {
    private final BaseErrorCode errorCode;
}