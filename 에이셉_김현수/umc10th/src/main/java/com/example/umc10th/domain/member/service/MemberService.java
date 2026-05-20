package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.FoodRepository;
import com.example.umc10th.domain.member.repository.MemberFoodRepository;
import com.example.umc10th.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResDTO.JoinResultDTO join(MemberReqDTO.JoinDTO dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Member member = MemberConverter.toMember(dto, encodedPassword);
        memberRepository.save(member);

        if (dto.getPreferCategory() != null && !dto.getPreferCategory().isEmpty()) {
            List<Food> foods = foodRepository.findAllByIdIn(dto.getPreferCategory());
            List<MemberFood> memberFoods = foods.stream()
                    .map(food -> MemberFood.builder().member(member).food(food).build())
                    .collect(Collectors.toList());
            memberFoodRepository.saveAll(memberFoods);
        }

        return MemberConverter.toJoinResultDTO(member);
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
