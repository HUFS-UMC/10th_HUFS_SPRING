package com.springboot.umc10thlea.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetailResDto {
    private Long reviewId;
    private String content;
    private Integer score;
    private LocalDateTime createdAt;
}
