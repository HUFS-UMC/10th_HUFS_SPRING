package com.wonho.sample_project.domains.review.dto;

import com.wonho.sample_project.domains.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ReviewRequestDTO {

    @AllArgsConstructor
    @Builder
    @Getter
    public static class GetReviews {
        private List<ReviewInfo> reviews;
        private Integer page;
        private Integer size;
        private Long totalElements;
        private Integer totalPages;
        private Boolean hasNext;

        @AllArgsConstructor
        @Builder
        @Getter
        public static class ReviewInfo {
            private Long reviewId;
            private Long storeId;
            private String storeName;
            private String content;
            private Integer star;
            private LocalDate createdAt;
            private Boolean inProgress;

            public static ReviewInfo fromUserReview(Review review){
                return ReviewInfo.builder()
                        .reviewId(review.getReview_id())
                        .storeId(review.getStore().getStore_id())
                        .storeName(review.getStore().getName())
                        .content(review.getContent())
                        .star(review.getStar())
                        .createdAt(review.getCreated_at())
                        .inProgress(review.getReply() == null)
                        .build();
            }
        }
    }
}
