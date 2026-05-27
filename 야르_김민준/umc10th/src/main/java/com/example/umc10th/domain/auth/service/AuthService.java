package com.example.umc10th.domain.auth.service;

import com.example.umc10th.domain.auth.converter.AuthConverter;
import com.example.umc10th.domain.auth.dto.AuthReqDTO;
import com.example.umc10th.domain.auth.dto.AuthResDTO;
import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.Term;
import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.FoodRepository;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.member.repository.TermRepository;
import com.example.umc10th.domain.member.repository.mapping.MemberFoodRepository;
import com.example.umc10th.domain.member.repository.mapping.MemberTermRepository;
import com.example.umc10th.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;
    private final TermRepository termRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final MemberTermRepository memberTermRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Member signUp(AuthReqDTO.SignUp request) {
        String encodedPassword = passwordEncoder.encode(request.password());
        Member member = AuthConverter.toMember(request, encodedPassword);

        Member savedMember = memberRepository.save(member);

        // 선호 음식 매핑 저장
        List<Food> foodList = request.foodCategoryIds().stream()
                .map(id -> foodRepository.findById(id)
                        .orElseThrow(() -> new MemberException(MemberErrorCode.FOOD_CATEGORY_NOT_FOUND)))
                .toList();

        List<MemberFood> memberFoodList = foodList.stream()
                .map(food -> MemberFood.builder()
                        .member(savedMember)
                        .food(food)
                        .build())
                .collect(Collectors.toList());

        memberFoodRepository.saveAll(memberFoodList);

        // 약관 동의 매핑 저장
        List<Term> termList = request.termIds().stream()
                .map(id -> termRepository.findById(id)
                        .orElseThrow(() -> new MemberException(MemberErrorCode.TERM_NOT_FOUND)))
                .toList();

        List<MemberTerm> memberTermList = termList.stream()
                .map(term -> MemberTerm.builder()
                        .member(savedMember)
                        .term(term)
                        .build())
                .collect(Collectors.toList());

        memberTermRepository.saveAll(memberTermList);

        return savedMember;
    }

    public AuthResDTO.LoginResult login(AuthReqDTO.Login request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }

        String token = jwtTokenProvider.createToken(member.getEmail());

        return AuthResDTO.LoginResult.builder()
                .memberId(member.getId())
                .accessToken(token)
                .build();
    }
}
