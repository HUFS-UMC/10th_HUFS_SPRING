package com.example.umc10th.domain.review.dto;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {
    @Builder
    public record ReviewCreateResultDTO(
            Long reviewId,
            LocalDateTime createdAt
    ) {}

    @Builder
    public record MyReviewListDTO(
            List<MyReviewDetailDTO> reviewList,
            Integer listSize,
            Long nextCursorId,
            Float nextCursorStar,
            Boolean hasNext
    ) {}

    @Builder
    public record MyReviewDetailDTO(
            Long reviewId,
            String storeName,
            String content,
            Float star,
            LocalDateTime createdAt
    ) {}
}