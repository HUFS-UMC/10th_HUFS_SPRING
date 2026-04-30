package com.springboot.umc10thlea.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionHomeDetailResDto {
    private Long missionId;
    private String title;
    private Integer point;
    private String dDay; // 비즈니스 로직으로 계산
}
