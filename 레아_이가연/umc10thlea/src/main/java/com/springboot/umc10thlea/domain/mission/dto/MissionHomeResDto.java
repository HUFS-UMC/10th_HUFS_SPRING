package com.springboot.umc10thlea.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionHomeResDto {
    private String regionName;
    private Integer regionProgress;
    private List<MissionHomeDetailResDto> missions;
}