package com.springboot.umc10thlea.domain.member.service;

import com.springboot.umc10thlea.domain.member.dto.MemberMissionDetailResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMissionListResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMyPageResDto;
import com.springboot.umc10thlea.domain.member.converter.MemberConverter;
import com.springboot.umc10thlea.domain.member.entity.Member;
import com.springboot.umc10thlea.domain.member.entity.mapping.MemberMission;
import com.springboot.umc10thlea.domain.member.repository.MemberMissionRepository;
import com.springboot.umc10thlea.domain.member.repository.MemberRepository;
import com.springboot.umc10thlea.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private static final String CHALLENGING_STATUS = "CHALLENGING";

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewRepository reviewRepository;

    // 마이페이지
    public MemberMyPageResDto getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));

        Long reviewCount = reviewRepository.countByMemberId(memberId);

        return MemberMyPageResDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .point(member.getPoint())
                .reviewCount(reviewCount)
                .build();
    }

    // 내가 진행중인 미션 페이징 조회
    public MemberMissionListResDto getMyChallengingMissions(Long userId, Integer pageNumber, Integer pageSize) {
        Page<MemberMission> missionPage = memberMissionRepository.findMyMissionsByStatus(
                userId,
                CHALLENGING_STATUS,
                PageRequest.of(pageNumber, pageSize));

        return MemberConverter.toMemberMissionListResDto(missionPage);
    }
}
