package com.example.umc10th.domain.auth.controller;

import com.example.umc10th.domain.auth.dto.AuthReqDTO;
import com.example.umc10th.domain.auth.dto.AuthResDTO;
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

    // 다음 주차에 구현
    //private final AuthService authService; // AuthService 의존성 주입

    // 회원가입 API
    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "회원가입을 진행합니다.")
    public ApiResponse<AuthResDTO.SignUpResult> signUp(
            @RequestBody @Valid AuthReqDTO.SignUp request
    ) {
        // 성공 코드는 GeneralSuccessCode 또는 별도의 AuthSuccessCode 사용
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}