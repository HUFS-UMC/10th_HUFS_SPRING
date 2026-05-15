package com.example.umc10th.domain.mission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.time.LocalDate;

public class MissionReqDTO {

    @Getter
    public static class CreateMissionDTO {
        @NotBlank
        String content;   // 미션 내용
        @NotNull
        Integer reward;   // 리워드 포인트
        @NotNull
        LocalDate deadline; // 미션 마감일
    }

    @Getter
    public static class ChallengeMissionDTO {
        @NotNull
        Long memberId; // 인증 구현 전 임시 필드
    }
}
