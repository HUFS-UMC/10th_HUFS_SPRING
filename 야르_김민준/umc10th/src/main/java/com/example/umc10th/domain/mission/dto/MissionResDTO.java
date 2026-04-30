package com.example.umc10th.domain.mission.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    // 미션 목록 조회 응답 (가게별 미션 또는 내 미션)
    @Builder
    public record MissionInfo(
            Long missionId,
            String storeName,
            Integer point,
            String conditional,
            LocalDate deadline,
            Boolean isComplete // is_complete 매핑
    ) {}

    // 미션 목록 리스트를 감싸는 DTO
    @Builder
    public record MissionList(
            List<MissionInfo> missions,
            Integer listSize
    ) {}

    // 미션 성공 업데이트 응답
    @Builder
    public record MissionResult(
            Long memberMissionId,
            Boolean isComplete,
            LocalDateTime updatedAt
    ) {}
}
