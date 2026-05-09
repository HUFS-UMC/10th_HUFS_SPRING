package com.wonho.sample_project.domains.review.dto;

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
        }
    }
}
