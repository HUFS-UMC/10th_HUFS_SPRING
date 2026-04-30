package com.example.umc10th.domain.auth.dto;

import java.time.LocalDate;
import java.util.List;

public class AuthReqDTO {

    // 회원가입 요청 DTO
    public record SignUp(
            String name,
            String gender,
            LocalDate birth,
            String address,
            String detailAddress,
            List<Long> foodCategoryIds, // 선호 음식 카테고리
            List<Long> termIds          // 동의한 약관
    ) {}
}
