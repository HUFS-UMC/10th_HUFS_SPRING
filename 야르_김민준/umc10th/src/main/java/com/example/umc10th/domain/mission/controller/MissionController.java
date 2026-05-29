package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/locations/{locationName}")
    @Operation(summary = "홈 화면 - 지역별 미션 목록 조회 API")
    public ApiResponse<MissionResDTO.MissionListDTO> getMissionsByLocation(
            @PathVariable String locationName,
            @RequestParam(name = "page", defaultValue = "0") Integer page) {

        Page<Mission> missionPage = missionService.getMissionsByLocation(locationName, page);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_FOUND,MissionConverter.toMissionListDTO(missionPage));
    }

    @GetMapping("/my-missions")
    @Operation(summary = "MY MISSION 화면 - 내 미션 목록 조회 API", description = "로그인한 사용자의 미션 목록을 조회합니다.")
    public ApiResponse<MissionResDTO.MemberMissionListDTO> getMyMissions(
            @Parameter(hidden = true) @AuthUser Member member,
            @RequestParam(name = "isComplete", defaultValue = "false") Boolean isComplete,
            @RequestParam(name = "page", defaultValue = "0") Integer page) {

        Page<MemberMission> memberMissionPage = missionService.getMyMissionsByComplete(member.getId(), isComplete, page);
        return ApiResponse.onSuccess(MissionSuccessCode.MEMBER_MISSION_FOUND,MissionConverter.toMemberMissionListDTO(memberMissionPage));
    }
}
