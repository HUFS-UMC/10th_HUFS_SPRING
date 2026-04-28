package com.springboot.umc10thlea.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberMissionCompleteResDto {
    private Long memberMissionId;
    private String status;
    private LocalDateTime updatedAt; // 완료 시 갱신 시간
}