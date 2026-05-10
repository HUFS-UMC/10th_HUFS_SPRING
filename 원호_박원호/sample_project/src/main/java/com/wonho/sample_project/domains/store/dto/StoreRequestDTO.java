package com.wonho.sample_project.domains.store.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public class StoreRequestDTO {
    @RequiredArgsConstructor
    @Getter
    public static class CreateReview {
        @NotNull
        @Length(min = 10)
        private final String content;

        @NotNull
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
