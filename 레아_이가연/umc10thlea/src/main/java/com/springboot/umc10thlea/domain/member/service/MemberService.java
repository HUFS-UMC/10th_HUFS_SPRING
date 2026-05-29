package com.springboot.umc10thlea.domain.member.service;

import com.springboot.umc10thlea.domain.member.dto.MemberMissionDetailResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMissionListResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberLoginReqDto;
import com.springboot.umc10thlea.domain.member.dto.MemberLoginResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMyPageResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberSignUpReqDto;
import com.springboot.umc10thlea.domain.member.dto.MemberSignUpResDto;
import com.springboot.umc10thlea.domain.member.converter.MemberConverter;
import com.springboot.umc10thlea.domain.member.entity.Member;
import com.springboot.umc10thlea.domain.member.entity.mapping.MemberMission;
import com.springboot.umc10thlea.domain.member.exception.MemberException;
import com.springboot.umc10thlea.domain.member.exception.code.MemberErrorCode;
import com.springboot.umc10thlea.domain.member.repository.MemberMissionRepository;
import com.springboot.umc10thlea.domain.member.repository.MemberRepository;
import com.springboot.umc10thlea.domain.review.repository.ReviewRepository;
import com.springboot.umc10thlea.global.apiPayload.code.GeneralErrorCode;
import com.springboot.umc10thlea.global.apiPayload.exception.ProjectException;
import com.springboot.umc10thlea.global.security.entity.AuthMember;
import com.springboot.umc10thlea.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public MemberSignUpResDto signUp(MemberSignUpReqDto request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = memberRepository.save(MemberConverter.toMember(request, encodedPassword));

        return MemberConverter.toMemberSignUpResDto(member);
    }

    public MemberLoginResDto login(MemberLoginReqDto request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.LOGIN_FAILED);
        }

        String accessToken = jwtUtil.createAccessToken(new AuthMember(member));
        return MemberConverter.toMemberLoginResDto(accessToken);
    }

    // 마이페이지
    public MemberMyPageResDto getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));

        Long reviewCount = reviewRepository.countByMemberId(memberId);

        return MemberConverter.toMemberMyPageResDto(member, reviewCount);
    }

    public MemberMyPageResDto getMyPage(AuthMember authMember) {
        if (authMember == null) {
            throw new ProjectException(GeneralErrorCode.UNAUTHORIZED);
        }

        Long memberId = authMember.getMember().getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Long reviewCount = reviewRepository.countByMemberId(memberId);

        return MemberConverter.toMemberMyPageResDto(member, reviewCount);
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
