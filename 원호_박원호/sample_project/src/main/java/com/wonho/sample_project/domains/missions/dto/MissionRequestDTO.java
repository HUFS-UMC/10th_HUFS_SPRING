package com.wonho.sample_project.domains.missions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MissionRequestDTO {
    @AllArgsConstructor
    @Builder
    @Getter
    public static class GetMissions {
        private List<MissionInfo> missions;
        private Integer page;
        private Integer size;
        private Long totalElements;
        private Integer totalPages;
        private Boolean hasNext;

        @AllArgsConstructor
        @Builder
        @Getter
        public static class MissionInfo {
            private Long missionId;
            private String storeName;
            private String regionName;
            private String conditional;
            private Integer point;
            private LocalDate deadline;
        }
    }
}
