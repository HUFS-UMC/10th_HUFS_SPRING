package com.example.umc10th.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class AuthReqDTO {

    // 회원가입 요청 DTO
    public record SignUp(
            @NotBlank(message = "이름은 필수 입력 항목입니다.")
            String name,
            @NotBlank(message = "성별은 필수 입력 항목입니다.")
            String gender,
            @NotNull(message = "생년월일은 필수 입력 항목입니다.")
            LocalDate birth,
            @NotBlank(message = "주소는 필수 입력 항목입니다.")
            String address,
            String detailAddress,
            @NotEmpty(message = "선호 음식 카테고리를 하나 이상 선택해야 합니다.")
            List<Long> foodCategoryIds, // 선호 음식 카테고리
            @NotEmpty(message = "약관 동의는 필수입니다.")
            List<Long> termIds          // 동의한 약관
    ) {}
}
