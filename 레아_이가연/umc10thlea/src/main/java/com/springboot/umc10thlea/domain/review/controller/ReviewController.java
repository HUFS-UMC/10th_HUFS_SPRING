package com.springboot.umc10thlea.domain.review.controller;

import com.springboot.umc10thlea.domain.review.dto.ReviewCreateReqDto;
import com.springboot.umc10thlea.domain.review.dto.ReviewCreateResDto;
import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @PostMapping("")
    public ApiResponse<ReviewCreateResDto> createReview(@RequestBody ReviewCreateReqDto request) {
        return ApiResponse.onSuccess(ReviewCreateResDto.builder()
                .reviewId(50L)
                .createdAt(LocalDateTime.now())
                .build());
    }
}