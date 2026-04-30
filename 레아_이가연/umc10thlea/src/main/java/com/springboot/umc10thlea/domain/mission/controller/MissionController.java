package com.springboot.umc10thlea.domain.mission.controller;

import com.springboot.umc10thlea.domain.mission.dto.MissionHomeDetailResDto;
import com.springboot.umc10thlea.domain.mission.dto.MissionHomeResDto;
import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    @GetMapping("/home")
    public ApiResponse<MissionHomeResDto> getHome(
            @RequestParam Long regionId, @RequestParam Integer page, @RequestParam Integer size) {

        MissionHomeDetailResDto mission = MissionHomeDetailResDto.builder()
                .missionId(101L)
                .title("맛 집 정복 미션")
                .point(500)
                .dDay("D-5")
                .build();

        return ApiResponse.onSuccess(MissionHomeResDto.builder()
                .regionName("서울 강남구")
                .regionProgress(45)
                .missions(List.of(mission))
                .build());
    }
}