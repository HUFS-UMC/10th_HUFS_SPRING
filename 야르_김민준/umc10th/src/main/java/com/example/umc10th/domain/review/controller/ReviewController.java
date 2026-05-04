package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode; // 성공 코드 import
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/")
    @Operation(summary = "리뷰 작성 API", description = "특정 가게에 사진을 제외한 텍스트 리뷰를 작성합니다.")
    public ApiResponse<ReviewResDTO.ReviewCreateResultDTO> createReview(@RequestBody ReviewReqDTO.ReviewCreateDTO request) {
        Review review = reviewService.createReview(request);

        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_CREATED, ReviewConverter.toReviewCreateResultDTO(review));
    }
}