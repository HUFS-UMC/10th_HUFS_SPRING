package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    public static MissionResDTO.MissionDetailDTO toMissionDetailDTO(Mission mission) {
        return MissionResDTO.MissionDetailDTO.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore() != null ? mission.getStore().getName() : "가게 이름 없음")
                .point(mission.getPoint())
                .conditional(mission.getConditional())
                .build();
    }

    public static MissionResDTO.MissionListDTO toMissionListDTO(Page<Mission> missionPage) {
        List<MissionResDTO.MissionDetailDTO> missionDetailDTOList = missionPage.stream()
                .map(MissionConverter::toMissionDetailDTO)
                .collect(Collectors.toList());

        return MissionResDTO.MissionListDTO.builder()
                .isLast(missionPage.isLast())
                .isFirst(missionPage.isFirst())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .listSize(missionDetailDTOList.size())
                .missionList(missionDetailDTOList)
                .build();
    }

    public static MissionResDTO.MemberMissionDetailDTO toMemberMissionDetailDTO(MemberMission memberMission) {
        Mission mission = memberMission.getMission();
        return MissionResDTO.MemberMissionDetailDTO.builder()
                .memberMissionId(memberMission.getId())
                .storeName(mission != null && mission.getStore() != null ? mission.getStore().getName() : "가게 이름 없음")
                .point(mission != null ? mission.getPoint() : 0)
                .conditional(mission != null ? mission.getConditional() : "조건 없음")
                .isComplete(memberMission.getIsComplete())
                .build();
    }

    public static MissionResDTO.MemberMissionListDTO toMemberMissionListDTO(Page<MemberMission> memberMissionPage) {
        List<MissionResDTO.MemberMissionDetailDTO> dtoList = memberMissionPage.stream()
                .map(MissionConverter::toMemberMissionDetailDTO)
                .collect(Collectors.toList());

        return MissionResDTO.MemberMissionListDTO.builder()
                .isLast(memberMissionPage.isLast())
                .isFirst(memberMissionPage.isFirst())
                .totalPage(memberMissionPage.getTotalPages())
                .totalElements(memberMissionPage.getTotalElements())
                .listSize(dtoList.size())
                .missionList(dtoList)
                .build();
    }
}