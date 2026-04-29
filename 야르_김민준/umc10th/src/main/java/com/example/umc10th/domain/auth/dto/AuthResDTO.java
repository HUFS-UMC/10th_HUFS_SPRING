package com.example.umc10th.domain.auth.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class AuthResDTO {

    // 회원가입 응답 DTO
    @Builder
    public record SignUpResult(
            Long memberId,
            LocalDateTime createdAt
    ) {}
}