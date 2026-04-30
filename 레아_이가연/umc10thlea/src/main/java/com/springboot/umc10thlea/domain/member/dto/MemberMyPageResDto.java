package com.springboot.umc10thlea.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberMyPageResDto {
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer point; // ERD상 Member 혹은 별도 포인트 테이블 연관
    private Long reviewCount;
}
