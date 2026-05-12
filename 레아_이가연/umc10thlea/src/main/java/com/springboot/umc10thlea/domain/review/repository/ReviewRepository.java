package com.springboot.umc10thlea.domain.review.repository;

import com.springboot.umc10thlea.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Long countByMemberId(Long memberId);

    @Query("""
            SELECT r FROM Review r
            WHERE r.member.id = :userId
              AND (:cursor = -1 OR r.id < :cursor)
            ORDER BY r.id DESC
            """)
    Slice<Review> findMyReviewsOrderByIdDesc(
            @Param("userId") Long userId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    @Query("""
            SELECT r FROM Review r
            WHERE r.member.id = :userId
              AND (
                  :cursorReviewId = -1
                  OR r.rating < :cursorScore
                  OR (r.rating = :cursorScore AND r.id < :cursorReviewId)
              )
            ORDER BY r.rating DESC, r.id DESC
            """)
    Slice<Review> findMyReviewsOrderByRatingDescAndIdDesc(
            @Param("userId") Long userId,
            @Param("cursorScore") Integer cursorScore,
            @Param("cursorReviewId") Long cursorReviewId,
            Pageable pageable
    );
}
