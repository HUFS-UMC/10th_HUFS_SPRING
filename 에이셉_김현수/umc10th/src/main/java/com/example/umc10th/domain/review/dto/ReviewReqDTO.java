package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.util.List;

public class ReviewReqDTO {

    @Getter
    public static class PostDTO {
        @NotNull
        Long memberId; // 인증 구현 전 임시 필드
        @NotBlank
        String content;
        @NotNull
        @DecimalMin(value = "0.0")
        @DecimalMax(value = "5.0")
        Float star;
        List<String> photoUrls;
    }
}
