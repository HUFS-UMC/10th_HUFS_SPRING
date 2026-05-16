package com.springboot.umc10thlea.domain.review.controller;

import com.springboot.umc10thlea.domain.review.dto.ReviewCreateReqDto;
import com.springboot.umc10thlea.domain.review.dto.ReviewCreateResDto;
import com.springboot.umc10thlea.domain.review.service.ReviewService;
import com.springboot.umc10thlea.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ApiResponse<ReviewCreateResDto> createReview(@Valid @RequestBody ReviewCreateReqDto request) {

        Long memberId = 1L; // JWT 도입 전까지 임시 고정
        return ApiResponse.onSuccess(reviewService.createReview(memberId, request));
    }
}
