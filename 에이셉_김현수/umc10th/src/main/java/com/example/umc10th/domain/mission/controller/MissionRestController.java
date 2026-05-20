package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "미션", description = "미션 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionRestController {

    private final MissionService missionService;

    @Operation(summary = "미션 등록", description = "특정 가게에 미션을 등록합니다.")
    @PostMapping("/{storeId}")
    public ApiResponse<MissionResDTO.CreateMissionResultDTO> createMission(
            @PathVariable(name = "storeId") Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMissionDTO request) {
        return ApiResponse.onSuccess(missionService.createMission(storeId, request));
    }

    @Operation(summary = "미션 도전", description = "특정 미션에 도전합니다.")
    @PostMapping("/{missionId}/challenge")
    public ApiResponse<MissionResDTO.ChallengeMissionResultDTO> challengeMission(
            @PathVariable(name = "missionId") Long missionId,
            @RequestBody @Valid MissionReqDTO.ChallengeMissionDTO request) {
        return ApiResponse.onSuccess(missionService.challengeMission(missionId, request));
    }
}
