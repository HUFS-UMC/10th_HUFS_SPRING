package com.example.umc10th.domain.review.dto;

import lombok.Getter;
import lombok.Setter;

public class ReviewReqDTO {
    @Getter
    @Setter
    public static class ReviewCreateDTO {
        private Long memberId;
        private Long storeId;
        private Float star;
        private String content;
    }
}