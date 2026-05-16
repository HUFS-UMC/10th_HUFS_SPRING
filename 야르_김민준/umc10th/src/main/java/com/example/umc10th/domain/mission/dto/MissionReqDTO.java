package com.example.umc10th.domain.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class MissionReqDTO {

    @Getter
    public static class MyMissionListReqDTO {
        @NotNull(message = "사용자 ID는 필수 입력 항목입니다.")
        @Schema(description = "사용자 ID", example = "1")
        private Long memberId;
    }
}
