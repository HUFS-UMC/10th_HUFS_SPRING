package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // [5주차] 마이페이지 리뷰 목록 — 오프셋 기반 (Page)
    // JOIN FETCH로 N+1 방지, countQuery 분리로 페이징 버그 차단
    @Query(
        value = "SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId ORDER BY r.createdAt DESC",
        countQuery = "SELECT COUNT(r) FROM Review r WHERE r.member.id = :memberId"
    )
    Page<Review> findAllByMemberIdWithStore(@Param("memberId") Long memberId, Pageable pageable);

    // [7주차] 커서 기반 — ID 순, 첫 페이지 (커서 없음)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId ORDER BY r.id DESC")
    Slice<Review> findSliceByMemberIdOrderByIdDesc(@Param("memberId") Long memberId, Pageable pageable);

    // [7주차] 커서 기반 — ID 순, 다음 페이지 (커서: lastId)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId AND r.id < :cursorId ORDER BY r.id DESC")
    Slice<Review> findSliceByMemberIdAndIdLessThan(@Param("memberId") Long memberId, @Param("cursorId") Long cursorId, Pageable pageable);

    // [7주차] 커서 기반 — STAR 순, 첫 페이지 (커서 없음)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findSliceByMemberIdOrderByStarDesc(@Param("memberId") Long memberId, Pageable pageable);

    // [7주차] 커서 기반 — STAR 순, 다음 페이지 (커서: star:id)
    // 동일 star 내에서 id DESC로 타이브레이킹 → 유니크한 커서 보장
    @Query("SELECT r FROM Review r JOIN FETCH r.store " +
           "WHERE r.member.id = :memberId " +
           "AND (r.star < :cursorStar OR (r.star = :cursorStar AND r.id < :cursorId)) " +
           "ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findSliceByMemberIdAndStarCursor(
            @Param("memberId") Long memberId,
            @Param("cursorStar") Float cursorStar,
            @Param("cursorId") Long cursorId,
            Pageable pageable);
}