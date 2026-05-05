package com.wonho.sample_project.domains.review.repository;

import com.wonho.sample_project.domains.review.entity.Review;
import com.wonho.sample_project.domains.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	Page<Review> findByUser(User user, Pageable pageable);
	Page<Review> findByUserAndReplyIsNull(User user, Pageable pageable);
	Page<Review> findByUserAndReplyIsNotNull(User user, Pageable pageable);
}

