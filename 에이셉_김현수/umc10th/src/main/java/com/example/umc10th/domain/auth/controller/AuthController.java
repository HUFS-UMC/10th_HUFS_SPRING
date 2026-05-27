package com.example.umc10th.domain.auth.controller;

import com.example.umc10th.domain.auth.dto.AuthReqDTO;
import com.example.umc10th.domain.auth.dto.AuthResDTO;
import com.example.umc10th.domain.auth.service.AuthService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그인 API.
 * 회원가입은 기존 MemberRestController(POST /members)에서 처리합니다.
 */
@Tag(name = "인증", description = "로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * POST /auth/login
     *
     * <p>이메일·비밀번호를 검증하고 JWT AccessToken을 반환합니다.
     * 이후 요청에서는 Authorization 헤더에 "Bearer {accessToken}" 형식으로 전달하세요.
     *
     * <p>응답 예시:
     * <pre>
     * {
     *   "isSuccess": true,
     *   "code": "COMMON200",
     *   "message": "요청에 성공하였습니다.",
     *   "result": {
     *     "accessToken": "eyJhbGciOiJIUzI1NiJ9..."
     *   }
     * }
     * </pre>
     */
    @Operation(summary = "로그인", description = "이메일·비밀번호로 로그인하고 JWT AccessToken을 발급받습니다.")
    @PostMapping("/login")
    public ApiResponse<AuthResDTO.LoginResultDTO> login(@RequestBody @Valid AuthReqDTO.LoginDTO request) {
        return ApiResponse.onSuccess(authService.login(request));
    }
}
