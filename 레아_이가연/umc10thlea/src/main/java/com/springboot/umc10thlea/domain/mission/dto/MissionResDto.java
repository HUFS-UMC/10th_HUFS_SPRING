package com.springboot.umc10thlea.domain.mission.dto;

import lombok.Builder;

public class MissionResDto {
    //가게 내 미션 조회
    @Builder
    public record GetMission(
            Long missionId,
            Integer point,
            String conditional
    ){}

}
