package com.wonho.sample_project.domains.review.service;

import com.wonho.sample_project.domains.review.entity.Review;
import com.wonho.sample_project.domains.review.repository.ReviewRepository;
import com.wonho.sample_project.domains.store.dto.StoreRequestDTO;
import com.wonho.sample_project.domains.store.entity.Store;
import com.wonho.sample_project.domains.store.repository.StoreRepository;
import com.wonho.sample_project.domains.user.entity.User;
import com.wonho.sample_project.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    /**
     * 리뷰 작성
     */
    @Transactional
    public StoreRequestDTO.CreateReviewResponse createReview(Long storeId, Long userId, StoreRequestDTO.CreateReview request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + storeId));

        Review review = Review.builder()
                .content(request.getContent())
                .star(request.getRating())
                .created_at(LocalDate.now())
                .user(user)
                .store(store)
                .build();

        Review savedReview = reviewRepository.save(review);

        return StoreRequestDTO.CreateReviewResponse.builder()
                .reviewId(savedReview.getReview_id())
                .content(savedReview.getContent())
                .star(savedReview.getStar())
                .createdAt(savedReview.getCreated_at())
                .userName(user.getName())
                .storeName(store.getName())
                .build();
    }
}

