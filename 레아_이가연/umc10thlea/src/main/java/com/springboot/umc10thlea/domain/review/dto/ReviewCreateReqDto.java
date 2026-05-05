package com.springboot.umc10thlea.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateReqDto {
    @JsonProperty("member_mission_id")
    private Long memberMissionId;

    @JsonProperty("store_id")
    private Long storeId;

    private Integer rating;
    private String content;
}