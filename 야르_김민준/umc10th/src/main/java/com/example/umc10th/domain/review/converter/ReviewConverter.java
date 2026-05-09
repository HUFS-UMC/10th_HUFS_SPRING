package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;

public class ReviewConverter {

    public static Review toReview(ReviewReqDTO.ReviewCreateDTO request, Member member, Store store) {
        return Review.builder()
                .member(member)
                .store(store)
                .star(request.getStar())
                .content(request.getContent())
                .build();
    }

    public static ReviewResDTO.ReviewCreateResultDTO toReviewCreateResultDTO(Review review) {
        return ReviewResDTO.ReviewCreateResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }
}