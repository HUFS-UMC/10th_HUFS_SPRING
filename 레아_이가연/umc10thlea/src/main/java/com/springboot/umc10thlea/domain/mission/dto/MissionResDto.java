package com.springboot.umc10thlea.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MissionResDto {
    //홈 화면 미션 조회
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Home {
        private String regionName;
        private Integer regionProgress;
        private List<HomeDetail> missions;
    }

    //홈 화면 미션 상세
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HomeDetail {
        private Long missionId;
        private String title;
        private Integer point;
        private String dDay;
    }

    //가게 내 미션 조회
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreMission {
        private Long missionId;
        private Integer point;
        private String conditional;
    }

}
