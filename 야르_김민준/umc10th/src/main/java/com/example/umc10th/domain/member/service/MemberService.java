package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // 마이 페이지: 내 정보 조회
    public Member getMyPageInfo(Long memberId) {
        // 활성화된 회원만 조회하도록 Repository에 만들어둔 메서드 활용
        return memberRepository.findActiveMemberById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}