package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.exception.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionCommandService;
import com.example.umc10th.domain.mission.service.MissionQueryService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/stores")
public class MissionController {

    private final MissionCommandService missionCommandService;
    private final MissionQueryService missionQueryService;

    // 가게 미션 생성
    // POST /api/v1/stores/{storeId}/missions
    @PostMapping("/{storeId}/missions")
    public ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMission dto
    ) {
        missionCommandService.createMission(storeId, dto);
        return ApiResponse.onSuccess(MissionSuccessCode.CREATED, null);
    }

    // 가게 내 미션들 조회 (커서 기반)
    // GET /api/v1/stores/{storeId}/missions?pageSize=3&cursor=-1&query=id
    @GetMapping("/{storeId}/missions")
    public ApiResponse<MissionResDTO.Pagination<MissionResDTO.GetMission>> getMissions(
            @PathVariable Long storeId,
            @RequestParam Integer pageSize,
            @RequestParam String cursor,
            @RequestParam String query
    ) {
        return ApiResponse.onSuccess(MissionSuccessCode.OK,
                missionQueryService.getMissions(storeId, pageSize, cursor, query));
    }
}
