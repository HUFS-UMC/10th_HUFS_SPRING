package com.wonho.sample_project.domains.review.service;

import com.wonho.sample_project.domains.review.dto.ReviewRequestDTO;
import com.wonho.sample_project.domains.review.entity.Review;
import com.wonho.sample_project.domains.review.repository.ReviewRepository;
import com.wonho.sample_project.domains.store.dto.StoreRequestDTO;
import com.wonho.sample_project.domains.store.entity.Store;
import com.wonho.sample_project.domains.store.repository.StoreRepository;
import com.wonho.sample_project.domains.user.entity.User;
import com.wonho.sample_project.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

        /**
         * 유저 리뷰 조회 (페이지네이션 + 진행중 여부 필터)
         */
        public ReviewRequestDTO.GetReviews getUserReviews(Long userId, Boolean inProgress, Integer page, Integer size) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

                Pageable pageable = PageRequest.of(page, size);
                Page<Review> reviewPage;

                if (inProgress == null) {
                        reviewPage = reviewRepository.findByUser(user, pageable);
                } else if (inProgress) {
                        reviewPage = reviewRepository.findByUserAndReplyIsNull(user, pageable);
                } else {
                        reviewPage = reviewRepository.findByUserAndReplyIsNotNull(user, pageable);
                }

                List<ReviewRequestDTO.GetReviews.ReviewInfo> reviewInfos = reviewPage.getContent().stream()
                                .map(review -> ReviewRequestDTO.GetReviews.ReviewInfo.builder()
                                                .reviewId(review.getReview_id())
                                                .storeId(review.getStore() != null ? review.getStore().getStore_id() : null)
                                                .storeName(review.getStore() != null ? review.getStore().getName() : null)
                                                .content(review.getContent())
                                                .star(review.getStar())
                                                .createdAt(review.getCreated_at())
                                                .inProgress(review.getReply() == null)
                                                .build())
                                .collect(Collectors.toList());

                return ReviewRequestDTO.GetReviews.builder()
                                .reviews(reviewInfos)
                                .page(reviewPage.getNumber())
                                .size(reviewPage.getSize())
                                .totalElements(reviewPage.getTotalElements())
                                .totalPages(reviewPage.getTotalPages())
                                .hasNext(reviewPage.hasNext())
                                .build();
        }
}

