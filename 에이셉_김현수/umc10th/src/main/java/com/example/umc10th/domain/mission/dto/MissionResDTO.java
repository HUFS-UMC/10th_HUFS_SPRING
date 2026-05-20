package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.MissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateMissionResultDTO {
        Long missionId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengeMissionResultDTO {
        Long memberMissionId;
        LocalDateTime createdAt;
    }

    // API 3: 내 미션 목록 — 단건 미리보기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPreviewDTO {
        Long memberMissionId;
        String storeName;
        String missionContent;
        Integer reward;
        LocalDate deadline;
        MissionStatus status;
    }

    // API 3: 내 미션 목록 — 페이지 래퍼
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPreviewListDTO {
        List<MissionPreviewDTO> missionList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    // API 4: 홈 화면 도전 가능 미션 — 단건 미리보기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HomeMissionPreviewDTO {
        Long missionId;
        String storeName;
        String missionContent;
        Integer reward;
        Integer minPrice;
        LocalDate deadline;
    }

    // API 4: 홈 화면 도전 가능 미션 — 페이지 래퍼
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HomeMissionPreviewListDTO {
        List<HomeMissionPreviewDTO> missionList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }
}
