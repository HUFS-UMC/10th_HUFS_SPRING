package com.springboot.umc10thlea.domain.member.dto;

import com.springboot.umc10thlea.domain.member.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원가입 요청")
public class MemberSignUpReqDto {
    @Valid
    @NotNull
    @Schema(description = "약관 동의 정보")
    private Agree agree;

    @NotBlank
    @Schema(description = "이름", example = "test")
    private String name;

    @NotNull
    @Schema(description = "성별", example = "MALE", allowableValues = {"MALE", "FEMALE"})
    private Gender gender;

    @Schema(description = "생년월일", example = "2026-03-23")
    private LocalDate birth;

    @Schema(description = "주소", example = "NONE")
    private String address;

    @Schema(description = "상세 주소", example = "string")
    private String detailAddress;

    @Schema(description = "선호 음식 목록", example = "[\"NONE\"]")
    private List<String> foodList;

    @NotBlank
    @Email
    @Schema(description = "이메일", example = "test@t")
    private String email;

    @NotBlank
    @Schema(description = "비밀번호", example = "test")
    private String password;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "약관 동의 요청")
    public static class Agree {
        @Schema(description = "만 14세 이상 동의", example = "true")
        private Boolean age;

        @Schema(description = "서비스 이용약관 동의", example = "true")
        private Boolean service;

        @Schema(description = "개인정보 처리방침 동의", example = "true")
        private Boolean privacy;

        @Schema(description = "위치정보 이용약관 동의", example = "false")
        private Boolean location;

        @Schema(description = "마케팅 정보 수신 동의", example = "false")
        private Boolean marketing;
    }
}
