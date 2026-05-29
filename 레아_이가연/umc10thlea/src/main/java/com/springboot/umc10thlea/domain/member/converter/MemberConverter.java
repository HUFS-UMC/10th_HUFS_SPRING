package com.springboot.umc10thlea.domain.member.converter;

import com.springboot.umc10thlea.domain.member.dto.MemberMissionDetailResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMissionListResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberLoginResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMyPageResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberSignUpReqDto;
import com.springboot.umc10thlea.domain.member.dto.MemberSignUpResDto;
import com.springboot.umc10thlea.domain.member.entity.Food;
import com.springboot.umc10thlea.domain.member.entity.Member;
import com.springboot.umc10thlea.domain.member.entity.Term;
import com.springboot.umc10thlea.domain.member.entity.mapping.MemberFood;
import com.springboot.umc10thlea.domain.member.entity.mapping.MemberMission;
import com.springboot.umc10thlea.domain.member.entity.mapping.MemberTerm;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {

    private static final String DEFAULT_ROLE = "USER";

    public static Member toMember(MemberSignUpReqDto request, String encodedPassword) {
        return Member.builder()
                .nickname(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .role(DEFAULT_ROLE)
                .build();
    }

    public static MemberSignUpResDto toMemberSignUpResDto(Member member) {
        return MemberSignUpResDto.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberLoginResDto toMemberLoginResDto(String accessToken) {
        return MemberLoginResDto.builder()
                .accessToken(accessToken)
                .build();
    }

    public static MemberMyPageResDto toMemberMyPageResDto(Member member, Long reviewCount) {
        return MemberMyPageResDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .point(member.getPoint())
                .reviewCount(reviewCount)
                .build();
    }

    public static MemberFood toMemberFood(Member member, Food food) {
        return MemberFood.builder()
                .member(member)
                .food(food)
                .build();
    }

    public static MemberTerm toMemberTerm(Member member, Term term, Boolean agreed) {
        return MemberTerm.builder()
                .member(member)
                .term(term)
                .agreed(agreed)
                .build();
    }

    public static MemberMissionDetailResDto toMemberMissionDetailResDto(MemberMission memberMission) {
        return MemberMissionDetailResDto.builder()
                .missionId(memberMission.getMission().getId())
                .point(memberMission.getMission().getPoint())
                .conditional(memberMission.getMission().getConditional())
                .build();
    }

    public static MemberMissionListResDto toMemberMissionListResDto(Page<MemberMission> missionPage) {
        List<MemberMissionDetailResDto> data = missionPage.getContent().stream()
                .map(MemberConverter::toMemberMissionDetailResDto)
                .collect(Collectors.toList());

        return MemberMissionListResDto.builder()
                .data(data)
                .pageNumber(missionPage.getNumber())
                .pageSize(missionPage.getSize())
                .build();
    }
}
