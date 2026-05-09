package com.springboot.umc10thlea.domain.member.service;

import com.springboot.umc10thlea.domain.member.dto.MemberMissionDetailResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMissionListResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMyPageResDto;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

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

    // 내 미션 페이징 조회
    public MemberMissionListResDto getMyMissions(Long memberId, String status, Integer page, Integer size) {
        Page<MemberMission> missionPage = memberMissionRepository.findMyMissionsByStatus(memberId, status, PageRequest.of(page - 1, size));

        List<MemberMissionDetailResDto> missionList = missionPage.getContent().stream()
                .map(mm -> MemberMissionDetailResDto.builder()
                        .memberMissionId(mm.getId())
                        .title(mm.getMission().getTitle())
                        .status(mm.getStatus())
                        .updatedAt(mm.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return MemberMissionListResDto.builder()
                .missionList(missionList)
                .listSize(missionList.size())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .build();
    }
}