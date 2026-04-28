package com.springboot.umc10thlea.domain.member.controller;

import com.springboot.umc10thlea.domain.member.dto.*;
import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @PostMapping("/signup")
    public ApiResponse<MemberSignUpResDto> signUp(@RequestBody MemberSignUpReqDto request) {
        return ApiResponse.onSuccess(MemberSignUpResDto.builder()
                .memberId(1L)
                .createdAt(LocalDateTime.now())
                .build());
    }

    @GetMapping("/mypage")
    public ApiResponse<MemberMyPageResDto> getMyPage() {
        return ApiResponse.onSuccess(MemberMyPageResDto.builder()
                .nickname("캠퍼")
                .email("test@example.com")
                .phoneNumber("01012345678")
                .point(1000)
                .reviewCount(5L)
                .build());
    }

    @GetMapping("/missions")
    public ApiResponse<MemberMissionListResDto> getMemberMissions(
            @RequestParam String status, @RequestParam Integer page, @RequestParam Integer size) {

        MemberMissionDetailResDto detail = MemberMissionDetailResDto.builder()
                .memberMissionId(1L)
                .title("환경 보호 미션")
                .status(status)
                .updatedAt(LocalDateTime.now())
                .build();

        return ApiResponse.onSuccess(MemberMissionListResDto.builder()
                .missionList(List.of(detail))
                .listSize(1)
                .totalPage(1)
                .totalElements(1L)
                .isFirst(true)
                .isLast(true)
                .build());
    }

    @PatchMapping("/missions/{memberMissionId}/complete")
    public ApiResponse<MemberMissionCompleteResDto> completeMission(@PathVariable Long memberMissionId) {
        return ApiResponse.onSuccess(MemberMissionCompleteResDto.builder()
                .memberMissionId(memberMissionId)
                .status("COMPLETED")
                .updatedAt(LocalDateTime.now())
                .build());
    }
}