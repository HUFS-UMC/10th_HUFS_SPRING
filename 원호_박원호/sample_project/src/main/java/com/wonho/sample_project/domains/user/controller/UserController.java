package com.wonho.sample_project.domains.user.controller;

import com.wonho.sample_project.domains.user.dto.UserRequestDTO;
import com.wonho.sample_project.global.api.ApiResponse;
import com.wonho.sample_project.global.api.code.BaseSuccessCode;
import com.wonho.sample_project.global.api.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @PostMapping("/")
    public ApiResponse<UserRequestDTO.CreateUser> createUser() {
        BaseSuccessCode code = GeneralSuccessCode.OK;

        // Todo: result 에 payload 넣기
        return ApiResponse.onSuccess(code, null);
    };

    @GetMapping("/:userId")
    public ApiResponse<UserRequestDTO.GetUser> getUser() {
        BaseSuccessCode code = GeneralSuccessCode.OK;

        // Todo: result 에 payload 넣기
        return ApiResponse.onSuccess(code, null);
    };

    @GetMapping("/:userId/missions")
    public ApiResponse<UserRequestDTO.GetMission> getMissions() {
        BaseSuccessCode code = GeneralSuccessCode.OK;

        // Todo: result 에 payload 넣기
        return ApiResponse.onSuccess(code,null);
    };

    @PostMapping("/:userId/missions/:missionId/complete")
    public ApiResponse<UserRequestDTO.UpdateMissionComplete> getMissionComplete() {
        BaseSuccessCode code = GeneralSuccessCode.OK;

        // Todo: result 에 payload 넣기
        return ApiResponse.onSuccess(code,null);
    };
}
