package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}/my-page")
    @Operation(summary = "마이 페이지 - 내 정보 조회 API")
    public ApiResponse<MemberResDTO.GetInfo> getMyPageInfo(@PathVariable Long memberId) {

        Member member = memberService.getMyPageInfo(memberId);

        return ApiResponse.onSuccess(MemberSuccessCode.OK,MemberConverter.toGetInfo(member));
    }
}