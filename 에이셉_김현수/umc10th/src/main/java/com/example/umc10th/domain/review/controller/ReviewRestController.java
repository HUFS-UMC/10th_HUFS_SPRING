package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "리뷰", description = "리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class ReviewRestController {

    @Operation(summary = "리뷰 작성", description = "특정 가게에 리뷰를 작성합니다.")
    @PostMapping("/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.PostResultDTO> postReview(
            @PathVariable(name = "storeId") Long storeId,
            @RequestBody @Valid ReviewReqDTO.PostDTO request) {
        return ApiResponse.onSuccess(null);
    }
}
