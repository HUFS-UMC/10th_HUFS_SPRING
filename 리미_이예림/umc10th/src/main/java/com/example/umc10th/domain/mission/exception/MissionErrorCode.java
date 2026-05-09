package com.example.umc10th.domain.mission.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    QUERY_NOT_VALID(HttpStatus.BAD_REQUEST, "MISSION400_1", "유효하지 않은 쿼리입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
