package com.example.umc10th.domain.review.dto;

import java.math.BigDecimal;
import java.util.List;

public class ReviewReqDTO {

    // 리뷰 작성 요청 DTO
    public record WriteReview(
            Long storeId,
            Long memberId,
            BigDecimal star, // star(BigDecimal) 매핑
            String content,
            List<String> reviewPhotoUrls // review_photo 테이블에 삽입될 URL들
    ) {}
}
