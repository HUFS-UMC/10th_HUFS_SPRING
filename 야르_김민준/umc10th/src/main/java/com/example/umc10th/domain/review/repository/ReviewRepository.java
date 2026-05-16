package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 특정 가게(Store)에 달린 리뷰 목록을 페이징 조회
    @Query("SELECT r FROM Review r WHERE r.store.id = :storeId")
    Page<Review> findReviewsByStoreId(@Param("storeId") Long storeId, Pageable pageable);

    // 내 리뷰 목록 조회 (ID 순 - 최신순)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId AND (:cursorId IS NULL OR r.id < :cursorId) ORDER BY r.id DESC")
    Slice<Review> findMyReviewsByIdCursor(@Param("memberId") Long memberId, @Param("cursorId") Long cursorId, Pageable pageable);

    // 내 리뷰 목록 조회 (별점 순 - 높은순)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId AND " +
            "(:cursorStar IS NULL OR (r.star < :cursorStar OR (r.star = :cursorStar AND (:cursorId IS NULL OR r.id < :cursorId)))) " +
            "ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findMyReviewsByRatingCursor(@Param("memberId") Long memberId, @Param("cursorStar") Float cursorStar, @Param("cursorId") Long cursorId, Pageable pageable);
}