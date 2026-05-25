package com.wonho.sample_project.domains.user.controller;

import com.wonho.sample_project.domains.user.dto.UserRequestDTO;
import com.wonho.sample_project.domains.user.entity.User;
import com.wonho.sample_project.domains.user.service.UserService;
import com.wonho.sample_project.global.api.ApiResponse;
import com.wonho.sample_project.global.api.code.BaseSuccessCode;
import com.wonho.sample_project.global.api.code.GeneralSuccessCode;
import com.wonho.sample_project.global.entity.AuthMember;
import com.wonho.sample_project.global.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

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
    public ApiResponse<UserRequestDTO.LoginUser> getUser(@PathVariable Long userId) {


        System.out.println("얘도작동...");
        BaseSuccessCode code = GeneralSuccessCode.OK;
        UserRequestDTO.LoginUser result = userService.getLoginUser(userId);

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
