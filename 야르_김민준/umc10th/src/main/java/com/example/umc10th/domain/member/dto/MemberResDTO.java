package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import lombok.Builder;
import java.util.List;

public class MemberResDTO {

    // 마이페이지 응답 DTO
    @Builder
    public record GetInfo(
            String name,
            String profileUrl,
            String email,
            String phoneNumber,
            Integer point
    ) {}

    // 홈 화면 조회 응답 DTO
    @Builder
    public record HomeView(
            String name,
            Integer point,
            List<MissionResDTO.MissionDetailDTO> activeMissions // MissionInfo 대신 MissionDetailDTO 사용
    ) {}
}