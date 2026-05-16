package com.wonho.sample_project.domains.missions.dto;

import com.wonho.sample_project.domains.missions.entity.Mission;
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

            public static MissionInfo fromMission(Mission mission){
                return MissionInfo.builder()
                        .missionId(mission.getMission_id())
                        .storeName(mission.getStore().getName())
                        .regionName(mission.getStore().getRegion().getName())
                        .conditional(mission.getConditional())
                        .point(mission.getPoint())
                        .deadline(mission.getDeadline())
                        .build();
            }
        }
    }
}
