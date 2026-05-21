package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;

public class MemberConverter {

    public static Member toMember(MemberReqDTO.JoinDTO dto, String encodedPassword) {
        return Member.builder()
                .name(dto.getName())
                .gender(dto.getGender())
                .birthYear(dto.getBirthYear())
                .birthMonth(dto.getBirthMonth())
                .birthDay(dto.getBirthDay())
                .address(dto.getAddress())
                .detailAddress(dto.getDetailAddress())
                .email(dto.getEmail())
                .password(encodedPassword)
                .build();
    }

    public static MemberResDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return MemberResDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
