package com.springboot.umc10thlea.domain.review.repository;

import com.springboot.umc10thlea.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Long countByMemberId(Long memberId);
}