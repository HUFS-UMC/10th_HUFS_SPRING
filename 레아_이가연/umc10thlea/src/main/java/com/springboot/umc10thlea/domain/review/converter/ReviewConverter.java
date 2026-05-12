package com.springboot.umc10thlea.domain.review.converter;

import com.springboot.umc10thlea.domain.review.dto.ReviewDetailResDto;
import com.springboot.umc10thlea.domain.review.dto.ReviewListResDto;
import com.springboot.umc10thlea.domain.review.entity.Review;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    private static final String SCORE_SORT = "score";

    public static ReviewDetailResDto toReviewDetailResDto(Review review) {
        return ReviewDetailResDto.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .score(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewListResDto toReviewListResDto(Slice<Review> reviewSlice, String sort, Integer pageSize) {
        List<Review> reviews = reviewSlice.getContent();
        boolean hasNext = reviews.size() > pageSize || reviewSlice.hasNext();

        List<ReviewDetailResDto> data = reviews.stream()
                .limit(pageSize)
                .map(ReviewConverter::toReviewDetailResDto)
                .collect(Collectors.toList());

        return ReviewListResDto.builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(createNextCursor(data, sort))
                .pageSize(pageSize)
                .build();
    }

    private static String createNextCursor(List<ReviewDetailResDto> data, String sort) {
        if (data.isEmpty()) {
            return null;
        }

        ReviewDetailResDto lastReview = data.get(data.size() - 1);
        if (SCORE_SORT.equals(sort)) {
            return lastReview.getScore() + ":" + lastReview.getReviewId();
        }
        return String.valueOf(lastReview.getReviewId());
    }
}
