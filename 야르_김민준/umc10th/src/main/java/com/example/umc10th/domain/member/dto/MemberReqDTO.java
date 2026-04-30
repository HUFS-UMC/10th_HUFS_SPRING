package com.example.umc10th.domain.member.dto;

public class MemberReqDTO {

    // 마이페이지 조회 요청 DTO
    public record GetInfo(
            Long id
    ) {}
}