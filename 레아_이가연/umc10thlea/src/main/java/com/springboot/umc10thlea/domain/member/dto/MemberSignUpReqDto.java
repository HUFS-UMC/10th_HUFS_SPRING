package com.springboot.umc10thlea.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpReqDto {
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String gender;
    private LocalDate birthDate;
    private String address;
}
