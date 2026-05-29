package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.repository.mapping.MemberMissionRepository;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final MemberMissionRepository memberMissionRepository;

    // 마이 페이지: 내 정보 조회
    public MemberResDTO.GetInfo getMyPageInfo(Long memberId) {
        Member member = memberRepository.findActiveMemberById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 최신 리뷰 3개 (Slice에서 content 추출)
        List<Review> reviews = reviewRepository.findMyReviewsByIdCursor(memberId, null, PageRequest.of(0, 3)).getContent();

        // 진행 중인 미션 3개 (Page에서 content 추출)
        List<MemberMission> missions = memberMissionRepository.findMyMissions(memberId, false, PageRequest.of(0, 3)).getContent();

        return MemberConverter.toGetInfo(member, reviews, missions);
    }
}
