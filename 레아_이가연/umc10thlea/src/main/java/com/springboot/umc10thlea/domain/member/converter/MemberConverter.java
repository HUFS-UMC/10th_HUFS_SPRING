package com.springboot.umc10thlea.domain.member.converter;

import com.springboot.umc10thlea.domain.member.dto.MemberMissionDetailResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMissionListResDto;
import com.springboot.umc10thlea.domain.member.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {

    public static MemberMissionDetailResDto toMemberMissionDetailResDto(MemberMission memberMission) {
        return MemberMissionDetailResDto.builder()
                .missionId(memberMission.getMission().getId())
                .point(memberMission.getMission().getPoint())
                .conditional(memberMission.getMission().getConditional())
                .build();
    }

    public static MemberMissionListResDto toMemberMissionListResDto(Page<MemberMission> missionPage) {
        List<MemberMissionDetailResDto> data = missionPage.getContent().stream()
                .map(MemberConverter::toMemberMissionDetailResDto)
                .collect(Collectors.toList());

        return MemberMissionListResDto.builder()
                .data(data)
                .pageNumber(missionPage.getNumber())
                .pageSize(missionPage.getSize())
                .build();
    }
}
