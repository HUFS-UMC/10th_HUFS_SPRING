package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/")
    @Operation(summary = "리뷰 작성 API", description = "특정 가게에 사진을 제외한 텍스트 리뷰를 작성합니다.")
    public ApiResponse<ReviewResDTO.ReviewCreateResultDTO> createReview(@RequestBody @Valid ReviewReqDTO.ReviewCreateDTO request) {
        Review review = reviewService.createReview(request);

        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_CREATED, ReviewConverter.toReviewCreateResultDTO(review));
    }

    @PostMapping("/mine")
    @Operation(summary = "내가 생성한 리뷰 목록 조회 API", description = "내가 작성한 리뷰 목록을 커서 기반 페이지네이션으로 조회합니다. 정렬 조건(orderBy)은 'id'(최신순) 또는 'rating'(별점순)입니다. 사용자 ID와 커서는 Request Body로 전달합니다.")
    public ApiResponse<ReviewResDTO.MyReviewListDTO> getMyReviews(
            @RequestBody @Valid ReviewReqDTO.MyReviewListReqDTO request,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        Slice<Review> reviewSlice;
        if ("rating".equalsIgnoreCase(orderBy)) {
            reviewSlice = reviewService.getMyReviewsByRating(request.getMemberId(), request.getCursorStar(), request.getCursorId(), size);
        } else {
            reviewSlice = reviewService.getMyReviewsById(request.getMemberId(), request.getCursorId(), size);
        }

        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_FOUND, ReviewConverter.toMyReviewListDTO(reviewSlice));
    }
}