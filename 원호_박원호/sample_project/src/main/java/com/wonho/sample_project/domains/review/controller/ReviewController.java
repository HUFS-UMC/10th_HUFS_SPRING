package com.wonho.sample_project.domains.review.controller;

import com.wonho.sample_project.domains.review.dto.ReviewRequestDTO;
import com.wonho.sample_project.domains.review.service.ReviewService;
import com.wonho.sample_project.domains.store.dto.StoreRequestDTO;
import com.wonho.sample_project.global.api.ApiResponse;
import com.wonho.sample_project.global.api.code.BaseSuccessCode;
import com.wonho.sample_project.global.api.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/stores/{storeId}")
    public ApiResponse<StoreRequestDTO.CreateReviewResponse> createReview(
            @PathVariable Long storeId,
            @RequestParam Long userId,
            @Valid @RequestBody StoreRequestDTO.CreateReview request) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        StoreRequestDTO.CreateReviewResponse result = reviewService.createReview(storeId, userId, request);
        return ApiResponse.onSuccess(code, result);
    }

    @GetMapping("/users/{userId}")
    public ApiResponse<ReviewRequestDTO.GetReviews> getUserReviews(
            @PathVariable Long userId,
            @RequestParam(required = false) Boolean inProgress,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        ReviewRequestDTO.GetReviews result = reviewService.getUserReviews(userId, inProgress, page, size);
        return ApiResponse.onSuccess(code, result);
    }
}
