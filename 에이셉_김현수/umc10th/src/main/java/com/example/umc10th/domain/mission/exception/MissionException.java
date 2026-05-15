package com.example.umc10th.domain.mission.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class MissionException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public MissionException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
