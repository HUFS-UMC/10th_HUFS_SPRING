package com.example.umc10th.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO {
        // 로그인 성공 시 클라이언트에게 발급하는 JWT AccessToken
        private String accessToken;
    }
}
