package com.springboot.umc10thlea.domain.member.controller;

import com.springboot.umc10thlea.domain.member.dto.MemberLoginReqDto;
import com.springboot.umc10thlea.domain.member.dto.MemberLoginResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberSignUpReqDto;
import com.springboot.umc10thlea.domain.member.dto.MemberSignUpResDto;
import com.springboot.umc10thlea.domain.member.exception.code.MemberSuccessCode;
import com.springboot.umc10thlea.domain.member.service.MemberService;
import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ApiResponse<MemberSignUpResDto> signUp(@Valid @RequestBody MemberSignUpReqDto request) {
        return ApiResponse.onSuccess(MemberSuccessCode.SIGNUP_SUCCESS, memberService.signUp(request));
    }

    @PostMapping("/login")
    public ApiResponse<MemberLoginResDto> login(@Valid @RequestBody MemberLoginReqDto request) {
        return ApiResponse.onSuccess(MemberSuccessCode.LOGIN_SUCCESS, memberService.login(request));
    }
}
