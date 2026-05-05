package com.springboot.umc10thlea.domain.mission.controller;

import com.springboot.umc10thlea.domain.mission.dto.MissionHomeDetailResDto;
import com.springboot.umc10thlea.domain.mission.dto.MissionHomeResDto;
import com.springboot.umc10thlea.domain.mission.service.MissionService;
import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/home")
    public ApiResponse<MissionHomeResDto> getHome(
            @RequestParam Long regionId,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        return ApiResponse.onSuccess(missionService.getHomeMissions(regionId, page, size));
    }
}