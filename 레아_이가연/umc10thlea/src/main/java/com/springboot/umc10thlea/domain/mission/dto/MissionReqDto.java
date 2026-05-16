package com.springboot.umc10thlea.domain.mission.dto;

import java.time.LocalDate;

public class MissionReqDto {
    //가게 미션 생성
    public record CreateMission(
            LocalDate deadline,
            Integer point,
            String conditional
    ) {
    }
}

