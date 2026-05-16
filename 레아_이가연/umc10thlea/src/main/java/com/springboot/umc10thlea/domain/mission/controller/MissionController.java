package com.springboot.umc10thlea.domain.mission.controller;

import com.springboot.umc10thlea.domain.mission.dto.MissionHomeDetailResDto;
import com.springboot.umc10thlea.domain.mission.dto.MissionHomeResDto;
import com.springboot.umc10thlea.domain.mission.dto.MissionReqDto;
import com.springboot.umc10thlea.domain.mission.dto.MissionResDto;
import com.springboot.umc10thlea.domain.mission.service.MissionService;
import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import com.springboot.umc10thlea.global.apiPayload.code.BaseSuccessCode;
import com.springboot.umc10thlea.global.apiPayload.code.MissionSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    //홈화면
    @GetMapping("/home/missions")
    public ApiResponse<MissionHomeResDto> getHome(
            @RequestParam Long regionId,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        return ApiResponse.onSuccess(missionService.getHomeMissions(regionId, page, size));
    }

    //가게 미션 생성
    @PostMapping("/stores/{storeId}")
    public ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody MissionReqDto.CreateMission dto
    ) {
        BaseSuccessCode code = MissionSuccessCode.CREATED;
        return ApiResponse.onSuccess(code, missionService.createMission(storeId, dto));
    }

    //가게 내 미션 조회
    @GetMapping("/stores/{storeId}/missions")
    public ApiResponse<List<MissionResDto.GetMission>> getMissions(
            @PathVariable Long storeId
    ){
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(storeId));
    }

}
