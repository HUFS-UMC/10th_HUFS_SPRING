package com.example.umc10th.domain.auth.controller;

import com.example.umc10th.domain.auth.converter.AuthConverter;
import com.example.umc10th.domain.auth.dto.AuthReqDTO;
import com.example.umc10th.domain.auth.dto.AuthResDTO;
import com.example.umc10th.domain.auth.service.AuthService;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth") // URI를 auth로 분리
public class AuthController {

    private final AuthService authService;

    // 회원가입 API
    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "회원가입을 진행합니다.")
    public ApiResponse<AuthResDTO.SignUpResult> signUp(
            @RequestBody @Valid AuthReqDTO.SignUp request
    ) {
        Member member = authService.signUp(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, AuthConverter.toSignUpResult(member));
    }

    // 로그인 API
    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인을 진행합니다.")
    public ApiResponse<AuthResDTO.LoginResult> login(
            @RequestBody @Valid AuthReqDTO.Login request
    ) {
        AuthResDTO.LoginResult result = authService.login(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }
}
