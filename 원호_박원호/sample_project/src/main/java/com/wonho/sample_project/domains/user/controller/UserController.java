package com.wonho.sample_project.domains.user.controller;

import com.wonho.sample_project.domains.user.dto.UserRequestDTO;
import com.wonho.sample_project.domains.user.entity.User;
import com.wonho.sample_project.domains.user.service.UserService;
import com.wonho.sample_project.global.api.ApiResponse;
import com.wonho.sample_project.global.api.code.BaseSuccessCode;
import com.wonho.sample_project.global.api.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseBody
    public ApiResponse<UserRequestDTO.CreateUser> createUser(@Valid @RequestBody UserRequestDTO.CreateUser createUser) {
        BaseSuccessCode code = GeneralSuccessCode.OK;

        User user = userService.createUser(createUser);

        // Todo: result 에 payload 넣기
        return ApiResponse.onSuccess(code, null);
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public ApiResponse<UserRequestDTO.GetUser> getUser(@PathVariable Long userId) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        UserRequestDTO.GetUser result = userService.getUser(userId);
        return ApiResponse.onSuccess(code, result);
    }

    @GetMapping("/{userId}/missions")
    @ResponseBody
    public ApiResponse<UserRequestDTO.GetMission> getMissions(
            @PathVariable Long userId,
            @RequestParam(required = false) Boolean isCompleted,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        UserRequestDTO.GetMission result = userService.getUserMissions(userId, isCompleted, page, size);
        return ApiResponse.onSuccess(code, result);
    }

    @PostMapping("/{userId}/missions/{missionId}/complete")
    @ResponseBody
    public ApiResponse<UserRequestDTO.UpdateMissionComplete> getMissionComplete(
            @PathVariable Long userId,
            @PathVariable Long missionId) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        UserRequestDTO.UpdateMissionComplete result = userService.completeMission(userId, missionId);
        return ApiResponse.onSuccess(code, result);
    }
}
