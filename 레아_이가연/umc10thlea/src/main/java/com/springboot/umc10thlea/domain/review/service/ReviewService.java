package com.springboot.umc10thlea.domain.review.service;

import com.springboot.umc10thlea.domain.member.entity.Member;
import com.springboot.umc10thlea.domain.member.repository.MemberRepository;
import com.springboot.umc10thlea.domain.mission.entity.Store;
import com.springboot.umc10thlea.domain.mission.repository.StoreRepository;
import com.springboot.umc10thlea.domain.review.dto.ReviewCreateReqDto;
import com.springboot.umc10thlea.domain.review.dto.ReviewCreateResDto;
import com.springboot.umc10thlea.domain.review.entity.Review;
import com.springboot.umc10thlea.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

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
}