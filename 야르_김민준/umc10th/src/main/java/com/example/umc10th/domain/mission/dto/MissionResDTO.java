package com.example.umc10th.domain.mission.dto;

import lombok.Builder;
import java.util.List;

public class MissionResDTO {

    // 홈 화면: 지역별 도전 가능 미션 목록 조회
    @Builder
    public record MissionListDTO(
            List<MissionDetailDTO> missionList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {}

    @Builder
    public record MissionDetailDTO(
            Long missionId,
            String storeName,
            Integer point,
            String conditional
    ) {}

    // MY MISSION 화면: 내 미션 진행/완료 목록 조회
    @Builder
    public record MemberMissionListDTO(
            List<MemberMissionDetailDTO> missionList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {}

    @Builder
    public record MemberMissionDetailDTO(
            Long memberMissionId,
            String storeName,
            Integer point,
            String conditional,
            Boolean isComplete
    ) {}
}