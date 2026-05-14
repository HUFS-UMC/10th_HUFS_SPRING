package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.util.List;

public class MemberReqDTO {

    @Getter
    public static class JoinDTO {
        @NotBlank
        String name;
        @NotNull
        Gender gender;
        @NotNull
        Integer birthYear;
        @NotNull
        Integer birthMonth;
        @NotNull
        Integer birthDay;
        @NotBlank
        String address;
        @NotBlank
        String detailAddress;
        List<Long> preferCategory;
    }
}