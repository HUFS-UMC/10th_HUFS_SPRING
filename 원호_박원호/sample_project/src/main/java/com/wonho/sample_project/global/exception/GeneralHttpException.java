package com.wonho.sample_project.global.exception;

import com.wonho.sample_project.global.api.code.BaseErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralHttpException extends RuntimeException {
    BaseErrorCode errorCode;

    public GeneralHttpException(BaseErrorCode errorCode) {
        super(errorCode.getMessage().toString());
        this.errorCode = errorCode;
    }
}
