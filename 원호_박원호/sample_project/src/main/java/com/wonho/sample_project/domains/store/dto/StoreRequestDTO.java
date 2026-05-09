package com.wonho.sample_project.domains.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

public class StoreRequestDTO {
    @RequiredArgsConstructor
    @Getter
    public static class CreateReview {
        private final String content;
        private final Integer rating;
    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class CreateReviewResponse {
        private Long reviewId;
        private String content;
        private Integer star;
        private LocalDate createdAt;
        private String userName;
        private String storeName;
    }
}
