package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.entity.Review;

import java.util.List;

public class MemberConverter {

    public static MemberResDTO.GetInfo toGetInfo(Member member, List<Review> reviews, List<MemberMission> missions) {
        return MemberResDTO.GetInfo.builder()
                .name(member.getName())
                .profileUrl(member.getProfileUrl())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .point(member.getPoint())
                .reviews(reviews.stream()
                        .map(ReviewConverter::toMyReviewDetailDTO)
                        .toList())
                .missions(missions.stream()
                        .map(mm -> MissionConverter.toMissionDetailDTO(mm.getMission()))
                        .toList())
                .build();
    }
}
