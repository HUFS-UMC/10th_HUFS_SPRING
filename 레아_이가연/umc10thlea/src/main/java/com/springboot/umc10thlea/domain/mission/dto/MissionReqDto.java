package com.springboot.umc10thlea.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class MissionReqDto {
    //가게 미션 생성
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateMission {
        private LocalDate deadline;
        private Integer point;
        private String conditional;
    }
}
