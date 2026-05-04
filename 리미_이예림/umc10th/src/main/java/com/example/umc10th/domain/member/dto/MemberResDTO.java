package com.example.umc10th.domain.member.dto;

import lombok.Builder;

public class MemberResDTO {
    @Builder // 빌더 패턴 사용 → MemberConverter에서 조립할 때 씀
    public record GetInfo(
            String name,
            String profileUrl,
            String email,
            String phoneNumber,
            Integer point) {
    }
}
