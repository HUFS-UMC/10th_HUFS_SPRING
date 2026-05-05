package com.wonho.sample_project.domains.store.controller;

import com.wonho.sample_project.domains.review.service.ReviewService;
import com.wonho.sample_project.domains.store.dto.StoreRequestDTO;
import com.wonho.sample_project.global.api.ApiResponse;
import com.wonho.sample_project.global.api.code.BaseSuccessCode;
import com.wonho.sample_project.global.api.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final ReviewService reviewService;

    @PostMapping("/{storeId}/review")
    public ApiResponse<StoreRequestDTO.CreateReviewResponse> createReview(
            @PathVariable Long storeId,
            @RequestParam Long userId,
            @RequestBody StoreRequestDTO.CreateReview request) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        StoreRequestDTO.CreateReviewResponse result = reviewService.createReview(storeId, userId, request);
        return ApiResponse.onSuccess(code, result);
    }
}
