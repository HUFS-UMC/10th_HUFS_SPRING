package com.springboot.umc10thlea.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그인 요청")
public class MemberLoginReqDto {
    @NotBlank
    @Email
    @Schema(description = "이메일", example = "test@t.t")
    private String email;

    @NotBlank
    @Schema(description = "비밀번호", example = "test")
    private String password;
}
