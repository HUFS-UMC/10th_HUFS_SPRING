package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    MISSION_FOUND(HttpStatus.OK, "MISSION2001", "미션 목록을 성공적으로 조회하였습니다."),
    MEMBER_MISSION_FOUND(HttpStatus.OK, "MISSION2002", "내 미션 목록을 성공적으로 조회하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}