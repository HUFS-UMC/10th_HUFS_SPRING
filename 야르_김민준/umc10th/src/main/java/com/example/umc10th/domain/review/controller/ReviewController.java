package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    // 다음 주차에 구현
    //private final ReviewService reviewService;

    // 리뷰 작성 API
    @PostMapping("")
    public ApiResponse<ReviewResDTO.WriteResult> writeReview(
            @RequestBody ReviewReqDTO.WriteReview request // Request Body 매핑 [cite: 165]
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}