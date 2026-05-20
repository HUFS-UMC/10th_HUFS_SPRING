package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewPhoto;
import com.example.umc10th.domain.review.enums.ReviewSortType;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.review.repository.ReviewPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    // [5주차] 가게에 리뷰 작성하기
    @Transactional
    public ReviewResDTO.PostResultDTO postReview(Long storeId, ReviewReqDTO.PostDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        Review review = ReviewConverter.toReview(dto, member, store);
        reviewRepository.save(review);

        if (dto.getPhotoUrls() != null && !dto.getPhotoUrls().isEmpty()) {
            List<ReviewPhoto> photos = dto.getPhotoUrls().stream()
                    .map(url -> ReviewPhoto.builder().photoUrl(url).review(review).build())
                    .collect(Collectors.toList());
            reviewPhotoRepository.saveAll(photos);
        }

        return ReviewConverter.toPostResultDTO(review);
    }

    // [5주차] 마이페이지 리뷰 목록 — 오프셋 기반 (Page, countQuery 포함)
    public ReviewResDTO.ReviewPreviewListDTO getMyReviews(Long memberId, Integer page) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        Page<Review> reviewPage = reviewRepository.findAllByMemberIdWithStore(
                memberId, PageRequest.of(page, 10)
        );
        return ReviewConverter.toReviewPreviewListDTO(reviewPage);
    }

    // [7주차] 마이페이지 리뷰 목록 — 커서 기반 (Slice, countQuery 없음)
    // cursor 파싱 규칙:
    //   - sortBy=ID   → cursor = "{id}"          예) "42"
    //   - sortBy=STAR → cursor = "{star}:{id}"   예) "3.5:42"
    public ReviewResDTO.CursorReviewListDTO getMyCursorReviews(
            Long memberId, String cursor, ReviewSortType sortBy, int size) {

        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        // Slice는 항상 page=0으로 고정, 커서 조건이 OFFSET 역할을 대신함
        PageRequest pageRequest = PageRequest.of(0, size);
        Slice<Review> slice;

        if (sortBy == ReviewSortType.STAR) {
            if (cursor == null || cursor.isBlank()) {
                slice = reviewRepository.findSliceByMemberIdOrderByStarDesc(memberId, pageRequest);
            } else {
                // "3.5:42" → cursorStar=3.5, cursorId=42
                String[] parts = cursor.split(":");
                Float cursorStar = Float.parseFloat(parts[0]);
                Long cursorId = Long.parseLong(parts[1]);
                slice = reviewRepository.findSliceByMemberIdAndStarCursor(memberId, cursorStar, cursorId, pageRequest);
            }
        } else {
            // ID 순 (기본)
            if (cursor == null || cursor.isBlank()) {
                slice = reviewRepository.findSliceByMemberIdOrderByIdDesc(memberId, pageRequest);
            } else {
                Long cursorId = Long.parseLong(cursor);
                slice = reviewRepository.findSliceByMemberIdAndIdLessThan(memberId, cursorId, pageRequest);
            }
        }

        return ReviewConverter.toCursorReviewListDTO(slice, sortBy);
    }
}