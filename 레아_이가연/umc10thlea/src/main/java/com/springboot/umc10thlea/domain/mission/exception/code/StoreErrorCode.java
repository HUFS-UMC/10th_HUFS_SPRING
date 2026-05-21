package com.springboot.umc10thlea.domain.mission.exception.code;

import com.springboot.umc10thlea.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND,
              "STORE 404_1",
            "해당 가게가 존재하지 않습니다."),
        ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
