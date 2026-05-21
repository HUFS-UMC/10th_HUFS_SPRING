package com.example.umc10th.domain.auth.converter;

import com.example.umc10th.domain.auth.dto.AuthReqDTO;
import com.example.umc10th.domain.auth.dto.AuthResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.mission.enums.Address;

import java.time.LocalDateTime;

public class AuthConverter {

    public static Member toMember(AuthReqDTO.SignUp request, String encodedPassword) {
        Gender gender = switch (request.gender().toUpperCase()) {
            case "MALE" -> Gender.MALE;
            case "FEMALE" -> Gender.FEMALE;
            default -> Gender.NONE;
        };

        Address address;
        try {
            address = Address.valueOf(request.address().toUpperCase());
        } catch (IllegalArgumentException e) {
            address = Address.NONE;
        }

        return Member.builder()
                .name(request.name())
                .email(request.email())
                .password(encodedPassword)
                .gender(gender)
                .birth(request.birth())
                .address(address)
                .detailAddress(request.detailAddress())
                .point(0)
                .socialUid("LOCAL") // For form login
                .phoneNumber("01000000000") // Placeholder as it's not in DTO yet
                .build();
    }

    public static AuthResDTO.SignUpResult toSignUpResult(Member member) {
        return AuthResDTO.SignUpResult.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
