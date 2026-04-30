package com.wonho.sample_project.domains.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class StoreRequestDTO {
    @RequiredArgsConstructor
    @Getter
    public static class CreateReview {
        private final String content;
        private final Integer rating;
    }
}
