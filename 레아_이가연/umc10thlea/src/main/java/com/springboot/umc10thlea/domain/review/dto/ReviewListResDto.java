package com.springboot.umc10thlea.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListResDto {
    private List<ReviewDetailResDto> data;
    private Boolean hasNext;
    private String nextCursor;
    private Integer pageSize;
}
