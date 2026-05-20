package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    public static Mission toMission(MissionReqDTO.CreateMissionDTO dto, Store store) {
        return Mission.builder()
                .content(dto.getContent())
                .reward(dto.getReward())
                .deadline(dto.getDeadline())
                .store(store)
                .build();
    }

    public static MissionResDTO.CreateMissionResultDTO toCreateMissionResultDTO(Mission mission) {
        return MissionResDTO.CreateMissionResultDTO.builder()
                .missionId(mission.getId())
                .createdAt(mission.getCreatedAt())
                .build();
    }

    public static MemberMission toMemberMission(Member member, Mission mission) {
        return MemberMission.builder()
                .member(member)
                .mission(mission)
                .build();
    }

    public static MissionResDTO.ChallengeMissionResultDTO toChallengeMissionResultDTO(MemberMission memberMission) {
        return MissionResDTO.ChallengeMissionResultDTO.builder()
                .memberMissionId(memberMission.getId())
                .createdAt(memberMission.getCreatedAt())
                .build();
    }

    public static MissionResDTO.MissionPreviewDTO toMissionPreviewDTO(MemberMission mm) {
        return MissionResDTO.MissionPreviewDTO.builder()
                .memberMissionId(mm.getId())
                .storeName(mm.getMission().getStore().getName())
                .missionContent(mm.getMission().getContent())
                .reward(mm.getMission().getReward())
                .deadline(mm.getMission().getDeadline())
                .status(mm.getStatus())
                .build();
    }

    public static MissionResDTO.MissionPreviewListDTO toMissionPreviewListDTO(Page<MemberMission> page) {
        List<MissionResDTO.MissionPreviewDTO> list = page.getContent().stream()
                .map(MissionConverter::toMissionPreviewDTO)
                .collect(Collectors.toList());

        return MissionResDTO.MissionPreviewListDTO.builder()
                .missionList(list)
                .listSize(list.size())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }

    public static MissionResDTO.HomeMissionPreviewDTO toHomeMissionPreviewDTO(Mission mission) {
        return MissionResDTO.HomeMissionPreviewDTO.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .missionContent(mission.getContent())
                .reward(mission.getReward())
                .minPrice(mission.getMinPrice())
                .deadline(mission.getDeadline())
                .build();
    }

    public static MissionResDTO.HomeMissionPreviewListDTO toHomeMissionPreviewListDTO(Page<Mission> page) {
        List<MissionResDTO.HomeMissionPreviewDTO> list = page.getContent().stream()
                .map(MissionConverter::toHomeMissionPreviewDTO)
                .collect(Collectors.toList());

        return MissionResDTO.HomeMissionPreviewListDTO.builder()
                .missionList(list)
                .listSize(list.size())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }
}
