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
public class MemberMissionDetailResDto {
    private Long memberMissionId;
    private String title; // Mission 테이블의 title
    private String status; // MemberMission 테이블의 status
    private LocalDateTime updatedAt;
}