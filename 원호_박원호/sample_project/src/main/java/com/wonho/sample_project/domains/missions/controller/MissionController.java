package com.wonho.sample_project.domains.missions.controller;

import com.wonho.sample_project.domains.missions.dto.MissionRequestDTO;
import com.wonho.sample_project.global.api.ApiResponse;
import com.wonho.sample_project.global.api.code.BaseSuccessCode;
import com.wonho.sample_project.global.api.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/missions")
public class MissionController {


    @GetMapping("/")
    public ApiResponse<MissionRequestDTO.GetMissions> getMissions() {
        BaseSuccessCode code = GeneralSuccessCode.OK;

        // Todo: result 에 payload 넣기
        return ApiResponse.onSuccess(code,null);
    };
}
