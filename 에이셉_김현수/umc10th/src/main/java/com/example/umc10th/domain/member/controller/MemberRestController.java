package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.enums.ReviewSortType;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberService memberService;
    private final ReviewService reviewService;
    private final MissionService missionService;

    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다.")
    @PostMapping
    public ApiResponse<MemberResDTO.JoinResultDTO> join(@RequestBody @Valid MemberReqDTO.JoinDTO request) {
        return ApiResponse.onSuccess(memberService.join(request));
    }

    // ── [5주차] 오프셋 기반 리뷰 목록 ──────────────────────────────────────
    @Operation(summary = "내 리뷰 목록 (오프셋)", description = "마이페이지: 내가 작성한 리뷰를 최신순으로 페이징 조회합니다.")
    @GetMapping("/{memberId}/reviews")
    public ApiResponse<ReviewResDTO.ReviewPreviewListDTO> getMyReviews(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "0") Integer page) {
        return ApiResponse.onSuccess(reviewService.getMyReviews(memberId, page));
    }

    // ── [7주차] 커서 기반 리뷰 목록 ──────────────────────────────────────────
    @Operation(
        summary = "내 리뷰 목록 (커서)",
        description = """
            마이페이지: 내가 작성한 리뷰를 커서 기반으로 조회합니다.
            - sortBy=ID   : cursor는 마지막 reviewId (예: "42")
            - sortBy=STAR : cursor는 "star:id" 형식 (예: "3.5:42")
            - 첫 요청 시 cursor 파라미터 생략
            """
    )
    @GetMapping("/{memberId}/reviews/cursor")
    public ApiResponse<ReviewResDTO.CursorReviewListDTO> getMyCursorReviews(
            @PathVariable Long memberId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "ID") ReviewSortType sortBy,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.onSuccess(reviewService.getMyCursorReviews(memberId, cursor, sortBy, size));
    }

    // ── [5주차] 오프셋 기반 미션 목록 (진행중 / 완료) ─────────────────────
    @Operation(summary = "내 미션 목록", description = "진행중(CHALLENGING) 또는 완료(COMPLETE) 미션을 페이징 조회합니다.")
    @GetMapping("/{memberId}/missions")
    public ApiResponse<MissionResDTO.MissionPreviewListDTO> getMyMissions(
            @PathVariable Long memberId,
            @RequestParam MissionStatus status,
            @RequestParam(defaultValue = "0") Integer page) {
        return ApiResponse.onSuccess(missionService.getMyMissions(memberId, status, page));
    }

    @Operation(summary = "홈 화면", description = "선택된 지역(locationId)에서 아직 도전하지 않은 미션 목록을 페이징 조회합니다.")
    @GetMapping("/{memberId}/home")
    public ApiResponse<MissionResDTO.HomeMissionPreviewListDTO> getHomeMissions(
            @PathVariable Long memberId,
            @RequestParam Long locationId,
            @RequestParam(defaultValue = "0") Integer page) {
        return ApiResponse.onSuccess(missionService.getHomeMissions(memberId, locationId, page));
    }
}