package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    // 리뷰 작성 (Create) - memberId를 파라미터로 받음
    @Transactional
    public Review createReview(ReviewReqDTO.ReviewCreateDTO request, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new MissionException(MissionErrorCode.STORE_NOT_FOUND));

        Review newReview = ReviewConverter.toReview(request, member, store);

        return reviewRepository.save(newReview);
    }

    public Slice<Review> getMyReviewsById(Long memberId, Long cursorId, Integer size) {
        return reviewRepository.findMyReviewsByIdCursor(memberId, cursorId, PageRequest.of(0, size));
    }

    public Slice<Review> getMyReviewsByRating(Long memberId, Float cursorStar, Long cursorId, Integer size) {
        return reviewRepository.findMyReviewsByRatingCursor(memberId, cursorStar, cursorId, PageRequest.of(0, size));
    }
}
