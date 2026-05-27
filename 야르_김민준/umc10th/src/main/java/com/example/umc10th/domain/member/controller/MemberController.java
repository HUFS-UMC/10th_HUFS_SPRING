package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/my-page")
    @Operation(summary = "마이 페이지 - 내 정보 조회 API", description = "로그인한 사용자의 마이페이지 정보를 조회합니다.")
    public ApiResponse<MemberResDTO.GetInfo> getMyPageInfo(
            @Parameter(hidden = true) @AuthUser Member member
    ) {
        MemberResDTO.GetInfo result = memberService.getMyPageInfo(member.getId());
        return ApiResponse.onSuccess(MemberSuccessCode.OK, result);
    }
}
