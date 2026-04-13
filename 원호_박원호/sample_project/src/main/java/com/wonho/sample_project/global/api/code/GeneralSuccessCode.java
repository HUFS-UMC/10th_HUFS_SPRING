package com.wonho.sample_project.global.api.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GeneralSuccessCode implements BaseErrorCode{
    OK(
            HttpStatus.OK,
            "COMMON200_1",
            "요청이 성공적으로 처리되었습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
