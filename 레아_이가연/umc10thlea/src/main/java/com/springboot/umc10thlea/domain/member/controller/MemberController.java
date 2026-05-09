package com.springboot.umc10thlea.domain.member.controller;

import com.springboot.umc10thlea.domain.member.dto.*;
import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import com.springboot.umc10thlea.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor // MemberService를 주입받기 위해 반드시 필요
public class MemberController {

    private final MemberService memberService;

    // --- [1] 회원가입 API  ---
    @PostMapping("/signup")
    public ApiResponse<MemberSignUpResDto> signUp(@RequestBody MemberSignUpReqDto request) {
        return ApiResponse.onSuccess(MemberSignUpResDto.builder()
                .memberId(1L)
                .createdAt(LocalDateTime.now())
                .build());
    }

    // --- [2] 마이페이지 조회 (Service 연동 완료) ---
    @GetMapping("/mypage")
    public ApiResponse<MemberMyPageResDto> getMyPage() {
        Long memberId = 1L; // JWT 도입 전까지 임시 유저 ID
        return ApiResponse.onSuccess(memberService.getMyPage(memberId));
    }

    // --- [3]  내 미션 목록 조회 (Service 연동 완료) ---
    @GetMapping("/missions")
    public ApiResponse<MemberMissionListResDto> getMemberMissions(
            @RequestParam String status,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Long memberId = 1L; // JWT 도입 전까지 임시 유저 ID
        return ApiResponse.onSuccess(memberService.getMyMissions(memberId, status, page, size));
    }

    // --- [4] 미션 완료 처리 API  ---
    @PatchMapping("/missions/{memberMissionId}/complete")
    public ApiResponse<MemberMissionCompleteResDto> completeMission(@PathVariable Long memberMissionId) {
        return ApiResponse.onSuccess(MemberMissionCompleteResDto.builder()
                .memberMissionId(memberMissionId)
                .status("COMPLETED")
                .updatedAt(LocalDateTime.now())
                .build());
    }
}