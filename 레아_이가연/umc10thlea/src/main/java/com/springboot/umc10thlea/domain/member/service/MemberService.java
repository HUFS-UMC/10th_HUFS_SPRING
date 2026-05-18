package com.springboot.umc10thlea.domain.member.service;

import com.springboot.umc10thlea.domain.member.dto.MemberMissionDetailResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMissionListResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberMyPageResDto;
import com.springboot.umc10thlea.domain.member.dto.MemberSignUpReqDto;
import com.springboot.umc10thlea.domain.member.dto.MemberSignUpResDto;
import com.springboot.umc10thlea.domain.member.converter.MemberConverter;
import com.springboot.umc10thlea.domain.member.entity.Food;
import com.springboot.umc10thlea.domain.member.entity.Member;
import com.springboot.umc10thlea.domain.member.entity.Term;
import com.springboot.umc10thlea.domain.member.entity.mapping.MemberFood;
import com.springboot.umc10thlea.domain.member.entity.mapping.MemberMission;
import com.springboot.umc10thlea.domain.member.entity.mapping.MemberTerm;
import com.springboot.umc10thlea.domain.member.exception.MemberException;
import com.springboot.umc10thlea.domain.member.exception.code.MemberErrorCode;
import com.springboot.umc10thlea.domain.member.repository.FoodRepository;
import com.springboot.umc10thlea.domain.member.repository.MemberFoodRepository;
import com.springboot.umc10thlea.domain.member.repository.MemberMissionRepository;
import com.springboot.umc10thlea.domain.member.repository.MemberRepository;
import com.springboot.umc10thlea.domain.member.repository.MemberTermRepository;
import com.springboot.umc10thlea.domain.member.repository.TermRepository;
import com.springboot.umc10thlea.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private static final String CHALLENGING_STATUS = "CHALLENGING";
    private static final List<String> TERM_NAMES = List.of("AGE", "SERVICE", "PRIVACY", "LOCATION", "MARKETING");

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;
    private final FoodRepository foodRepository;
    private final TermRepository termRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final MemberTermRepository memberTermRepository;

    @Transactional
    public MemberSignUpResDto signUp(MemberSignUpReqDto request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
        }

        validateRequiredTerms(request.getAgree());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = memberRepository.save(MemberConverter.toMember(request, encodedPassword));

        saveMemberFoods(member, request.getFoodList());
        saveMemberTerms(member, request.getAgree());

        return MemberConverter.toMemberSignUpResDto(member);
    }

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

    private void validateRequiredTerms(MemberSignUpReqDto.Agree agree) {
        if (!Boolean.TRUE.equals(agree.getAge())
                || !Boolean.TRUE.equals(agree.getService())
                || !Boolean.TRUE.equals(agree.getPrivacy())) {
            throw new MemberException(MemberErrorCode.REQUIRED_TERM_NOT_AGREED);
        }
    }

    private void saveMemberFoods(Member member, List<String> foodList) {
        if (foodList == null || foodList.isEmpty()) {
            return;
        }

        Set<String> foodNames = new LinkedHashSet<>(foodList);
        List<Food> foods = foodRepository.findAllByNameIn(foodNames);
        if (foods.size() != foodNames.size()) {
            throw new MemberException(MemberErrorCode.FOOD_NOT_FOUND);
        }

        List<MemberFood> memberFoods = foods.stream()
                .map(food -> MemberConverter.toMemberFood(member, food))
                .collect(Collectors.toList());
        memberFoodRepository.saveAll(memberFoods);
    }

    private void saveMemberTerms(Member member, MemberSignUpReqDto.Agree agree) {
        List<Term> terms = termRepository.findAllByNameIn(TERM_NAMES);
        if (terms.size() != TERM_NAMES.size()) {
            throw new MemberException(MemberErrorCode.TERM_NOT_FOUND);
        }

        Map<String, Term> termMap = terms.stream()
                .collect(Collectors.toMap(Term::getName, Function.identity()));

        List<MemberTerm> memberTerms = new ArrayList<>();
        for (String termName : TERM_NAMES) {
            memberTerms.add(MemberConverter.toMemberTerm(
                    member,
                    termMap.get(termName),
                    getAgreedByTermName(agree, termName)));
        }

        memberTermRepository.saveAll(memberTerms);
    }

    private Boolean getAgreedByTermName(MemberSignUpReqDto.Agree agree, String termName) {
        return switch (termName) {
            case "AGE" -> Boolean.TRUE.equals(agree.getAge());
            case "SERVICE" -> Boolean.TRUE.equals(agree.getService());
            case "PRIVACY" -> Boolean.TRUE.equals(agree.getPrivacy());
            case "LOCATION" -> Boolean.TRUE.equals(agree.getLocation());
            case "MARKETING" -> Boolean.TRUE.equals(agree.getMarketing());
            default -> false;
        };
    }
}
