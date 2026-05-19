package com.springboot.umc10thlea.domain.review.service;

import com.springboot.umc10thlea.domain.member.entity.Member;
import com.springboot.umc10thlea.domain.member.repository.MemberRepository;
import com.springboot.umc10thlea.domain.mission.entity.Store;
import com.springboot.umc10thlea.domain.mission.repository.StoreRepository;
import com.springboot.umc10thlea.domain.review.converter.ReviewConverter;
import com.springboot.umc10thlea.domain.review.dto.ReviewCreateReqDto;
import com.springboot.umc10thlea.domain.review.dto.ReviewCreateResDto;
import com.springboot.umc10thlea.domain.review.dto.ReviewListResDto;
import com.springboot.umc10thlea.domain.review.entity.Review;
import com.springboot.umc10thlea.domain.review.repository.ReviewRepository;
import com.springboot.umc10thlea.global.apiPayload.code.GeneralErrorCode;
import com.springboot.umc10thlea.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private static final String ID_SORT = "id";
    private static final String SCORE_SORT = "score";
    private static final String FIRST_CURSOR = "-1";

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public ReviewCreateResDto createReview(Long memberId, ReviewCreateReqDto request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        Review review = Review.builder()
                .member(member)
                .store(store)
                .rating(request.getRating())
                .content(request.getContent())
                .build();

        Review savedReview = reviewRepository.save(review);

        return ReviewCreateResDto.builder()
                .reviewId(savedReview.getId())
                .createdAt(savedReview.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public ReviewListResDto getMyReviews(Long userId, Integer pageSize, String cursor, String sort) {
        if (ID_SORT.equals(sort)) {
            Long cursorReviewId = parseIdCursor(cursor);
            Slice<Review> reviewSlice = reviewRepository.findMyReviewsOrderByIdDesc(
                    userId,
                    cursorReviewId,
                    PageRequest.of(0, pageSize + 1));
            return ReviewConverter.toReviewListResDto(reviewSlice, sort, pageSize);
        }

        if (SCORE_SORT.equals(sort)) {
            ScoreCursor scoreCursor = parseScoreCursor(cursor);
            Slice<Review> reviewSlice = reviewRepository.findMyReviewsOrderByRatingDescAndIdDesc(
                    userId,
                    scoreCursor.getScore(),
                    scoreCursor.getReviewId(),
                    PageRequest.of(0, pageSize + 1));
            return ReviewConverter.toReviewListResDto(reviewSlice, sort, pageSize);
        }

        throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
    }

    private Long parseIdCursor(String cursor) {
        try {
            return Long.parseLong(cursor);
        } catch (NumberFormatException e) {
            throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
        }
    }

    private ScoreCursor parseScoreCursor(String cursor) {
        if (FIRST_CURSOR.equals(cursor)) {
            return new ScoreCursor(Integer.MAX_VALUE, -1L);
        }

        String[] cursorValues = cursor.split(":");
        if (cursorValues.length != 2) {
            throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
        }

        try {
            return new ScoreCursor(
                    Integer.parseInt(cursorValues[0]),
                    Long.parseLong(cursorValues[1]));
        } catch (NumberFormatException e) {
            throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
        }
    }

    private static class ScoreCursor {
        private final Integer score;
        private final Long reviewId;

        private ScoreCursor(Integer score, Long reviewId) {
            this.score = score;
            this.reviewId = reviewId;
        }

        private Integer getScore() {
            return score;
        }

        private Long getReviewId() {
            return reviewId;
        }
    }
}
