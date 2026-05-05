package com.wonho.sample_project.domains.missions.controller;

import com.wonho.sample_project.domains.missions.dto.MissionRequestDTO;
import com.wonho.sample_project.domains.missions.service.MissionService;
import com.wonho.sample_project.global.api.ApiResponse;
import com.wonho.sample_project.global.api.code.BaseSuccessCode;
import com.wonho.sample_project.global.api.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/")
    public ApiResponse<MissionRequestDTO.GetMissions> getMissions(
            @RequestParam(required = false) Long regionId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        MissionRequestDTO.GetMissions result = missionService.getMissionsByRegion(regionId, page, size);
        return ApiResponse.onSuccess(code, result);
    }
}
