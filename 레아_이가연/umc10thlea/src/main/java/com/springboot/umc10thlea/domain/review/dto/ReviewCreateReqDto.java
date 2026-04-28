package com.springboot.umc10thlea.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateReqDto {
    private Long memberMissionId;
    private Long storeId;
    private Integer rating;
    private String content;
}