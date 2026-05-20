package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

        @NotBlank
        @Email
        String email;

        @NotBlank
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password;
    }
}
