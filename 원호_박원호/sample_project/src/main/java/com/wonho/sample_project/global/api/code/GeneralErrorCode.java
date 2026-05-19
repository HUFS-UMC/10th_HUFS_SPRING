package com.wonho.sample_project.global.api.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GeneralErrorCode implements BaseErrorCode{
    BAD_REQUEST(
            HttpStatus.BAD_REQUEST,
            "COMMON400_1",
            "잘못된 요청입니다."
    );

    private final HttpStatus status;
    private final String code;
    private final Object message;
}
