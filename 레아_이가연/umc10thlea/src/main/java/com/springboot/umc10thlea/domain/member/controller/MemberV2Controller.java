package com.springboot.umc10thlea.domain.member.controller;

import com.springboot.umc10thlea.domain.member.dto.MemberMyPageResDto;
import com.springboot.umc10thlea.domain.member.exception.code.MemberSuccessCode;
import com.springboot.umc10thlea.domain.member.service.MemberService;
import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import com.springboot.umc10thlea.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/users")
@RequiredArgsConstructor
public class MemberV2Controller {

    private final MemberService memberService;

    @Operation(
            summary = "마이페이지 조회",
            description = "Authorization 헤더에 Bearer accessToken이 필요한 API입니다.",
            security = @SecurityRequirement(name = "JWT TOKEN")
    )
    @GetMapping("/me")
    public ApiResponse<MemberMyPageResDto> getMyPage(
            @AuthenticationPrincipal AuthMember member
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.OK, memberService.getMyPage(member));
    }
}
