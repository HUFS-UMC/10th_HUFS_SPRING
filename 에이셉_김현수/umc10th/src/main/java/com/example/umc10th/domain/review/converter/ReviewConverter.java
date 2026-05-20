package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.enums.ReviewSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static Review toReview(ReviewReqDTO.PostDTO dto, Member member, Store store) {
        return Review.builder()
                .content(dto.getContent())
                .star(dto.getStar())
                .member(member)
                .store(store)
                .build();
    }

    public static ReviewResDTO.PostResultDTO toPostResultDTO(Review review) {
        return ReviewResDTO.PostResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResDTO.ReviewPreviewDTO toReviewPreviewDTO(Review review) {
        return ReviewResDTO.ReviewPreviewDTO.builder()
                .reviewId(review.getId())
                .storeName(review.getStore().getName())
                .body(review.getContent())
                .star(review.getStar())
                .createdAt(review.getCreatedAt())
                .build();
    }

    // [5주차] 오프셋 기반 변환 (Page → ReviewPreviewListDTO)
    public static ReviewResDTO.ReviewPreviewListDTO toReviewPreviewListDTO(Page<Review> reviewPage) {
        List<ReviewResDTO.ReviewPreviewDTO> reviewList = reviewPage.getContent().stream()
                .map(ReviewConverter::toReviewPreviewDTO)
                .collect(Collectors.toList());

        return ReviewResDTO.ReviewPreviewListDTO.builder()
                .reviewList(reviewList)
                .listSize(reviewList.size())
                .totalPage(reviewPage.getTotalPages())
                .totalElements(reviewPage.getTotalElements())
                .isFirst(reviewPage.isFirst())
                .isLast(reviewPage.isLast())
                .build();
    }

    // [7주차] 커서 기반 변환 (Slice → CursorReviewListDTO)
    // nextCursor 생성 규칙:
    //   - ID 순:   "{lastId}"            예) "42"
    //   - STAR 순: "{lastStar}:{lastId}" 예) "3.5:42"
    public static ReviewResDTO.CursorReviewListDTO toCursorReviewListDTO(
            Slice<Review> slice, ReviewSortType sortBy) {

        List<ReviewResDTO.ReviewPreviewDTO> reviewList = slice.getContent().stream()
                .map(ReviewConverter::toReviewPreviewDTO)
                .collect(Collectors.toList());

        String nextCursor = null;
        if (slice.hasNext() && !reviewList.isEmpty()) {
            Review last = slice.getContent().get(slice.getContent().size() - 1);
            nextCursor = (sortBy == ReviewSortType.STAR)
                    ? last.getStar() + ":" + last.getId()
                    : String.valueOf(last.getId());
        }

        return ReviewResDTO.CursorReviewListDTO.builder()
                .reviewList(reviewList)
                .listSize(reviewList.size())
                .hasNext(slice.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
