package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.MemberSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MemberController {

    // 다음 주차에 구현
    //private final MemberService memberService;

    // 마이페이지 조회 API
    @PostMapping("/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @RequestBody MemberReqDTO.GetInfo request
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.OK, null);
    }

    // 홈 화면 조회 API
    @GetMapping("/{userId}/home")
    public ApiResponse<MemberResDTO.HomeView> getHome(
            @PathVariable Long userId
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.OK, null);
    }
}
