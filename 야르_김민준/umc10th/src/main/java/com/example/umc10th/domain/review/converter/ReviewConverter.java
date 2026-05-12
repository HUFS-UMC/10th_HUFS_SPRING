package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

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

    public static ReviewResDTO.MyReviewDetailDTO toMyReviewDetailDTO(Review review) {
        return ReviewResDTO.MyReviewDetailDTO.builder()
                .reviewId(review.getId())
                .storeName(review.getStore().getName())
                .content(review.getContent())
                .star(review.getStar())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResDTO.MyReviewListDTO toMyReviewListDTO(Slice<Review> reviewSlice) {
        List<ReviewResDTO.MyReviewDetailDTO> myReviewDetailDTOList = reviewSlice.getContent().stream()
                .map(ReviewConverter::toMyReviewDetailDTO)
                .collect(Collectors.toList());

        Long nextCursorId = null;
        Float nextCursorStar = null;

        if (reviewSlice.hasNext() && !myReviewDetailDTOList.isEmpty()) {
            ReviewResDTO.MyReviewDetailDTO lastItem = myReviewDetailDTOList.get(myReviewDetailDTOList.size() - 1);
            nextCursorId = lastItem.reviewId();
            nextCursorStar = lastItem.star();
        }

        return ReviewResDTO.MyReviewListDTO.builder()
                .reviewList(myReviewDetailDTOList)
                .listSize(myReviewDetailDTOList.size())
                .hasNext(reviewSlice.hasNext())
                .nextCursorId(nextCursorId)
                .nextCursorStar(nextCursorStar)
                .build();
    }
}