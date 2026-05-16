package com.springboot.umc10thlea.domain.mission.converter;

import com.springboot.umc10thlea.domain.mission.dto.MissionReqDto;
import com.springboot.umc10thlea.domain.mission.dto.MissionResDto;
import com.springboot.umc10thlea.domain.mission.entity.Mission;
import com.springboot.umc10thlea.domain.mission.entity.Store;

public class MissionConverter {

    //가게 미션 생성
    public static Mission toMission(
            Store store,
            MissionReqDto.CreateMission dto
    ){
        return Mission.builder()
                .store(store)
                .conditional(dto.getConditional())
                .point(dto.getPoint())
                .deadline(dto.getDeadline())
                .build();
    }

    //가게 내 미션 조회
    public static MissionResDto.StoreMission toStoreMission(
        Mission mission
    ){
        return MissionResDto.StoreMission.builder()
                .conditional(mission.getConditional())
                .point(mission.getPoint())
                .missionId(mission.getId())
                .build();

    }
}
