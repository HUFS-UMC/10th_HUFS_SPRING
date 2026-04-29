package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class MissionController {

    // 다음 주차에 구현
    //private final MissionService missionService;

    // 4. 특정 가게의 미션 목록 조회
    @GetMapping("/stores/{storeId}")
    public ApiResponse<MissionResDTO.MissionList> getStoreMissions(
            @PathVariable Long storeId
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    // 5. 미션 성공(완료) 업데이트
    @PatchMapping("/{memberMissionId}/complete")
    public ApiResponse<MissionResDTO.MissionResult> completeMission(
            @PathVariable Long memberMissionId
    ) {
        // 상태를 변경하는 것이므로 PATCH 메서드 사용 권장
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}