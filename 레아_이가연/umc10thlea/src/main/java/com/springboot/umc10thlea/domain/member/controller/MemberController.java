package com.springboot.umc10thlea.domain.member.controller;

import com.springboot.umc10thlea.domain.member.dto.*;
import com.springboot.umc10thlea.domain.review.dto.ReviewListResDto;
import com.springboot.umc10thlea.domain.review.service.ReviewService;
import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import com.springboot.umc10thlea.domain.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor // MemberService를 주입받기 위해 반드시 필요
@Validated
public class MemberController {

    private final MemberService memberService;
    private final ReviewService reviewService;

    // --- [2] 마이페이지 조회 (Service 연동 완료) ---
    @GetMapping("/mypage")
    public ApiResponse<MemberMyPageResDto> getMyPage() {
        Long memberId = 1L; // JWT 도입 전까지 임시 유저 ID
        return ApiResponse.onSuccess(memberService.getMyPage(memberId));
    }

    // --- [3]  내가 진행중인 미션 목록 조회 (Service 연동 완료) ---
    @PostMapping("/missions")
    public ApiResponse<MemberMissionListResDto> getMemberMissions(
            @Valid @RequestBody MemberMissionListReqDto request) {
        return ApiResponse.onSuccess(memberService.getMyChallengingMissions(
                request.getUserId(),
                request.getPageNumber(),
                request.getPageSize()));
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

    // --- [5] 내가 생성한 리뷰 목록 조회 ---
    @GetMapping("/reviews")
    public ApiResponse<ReviewListResDto> getMemberReviews(
            @NotNull @RequestParam Long userId,
            @Min(1) @RequestParam Integer pageSize,
            @RequestParam String cursor,
            @RequestParam String sort) {
        return ApiResponse.onSuccess(reviewService.getMyReviews(userId, pageSize, cursor, sort));
    }
}
