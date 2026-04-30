package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class ReviewResDTO {

    // 리뷰 작성 응답 DTO
    @Builder
    public record WriteResult(
            Long reviewId,
            LocalDateTime createdAt
    ) {}
}
