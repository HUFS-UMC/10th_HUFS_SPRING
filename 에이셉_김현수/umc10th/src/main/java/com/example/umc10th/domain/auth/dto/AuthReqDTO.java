package com.example.umc10th.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class AuthReqDTO {

    @Getter
    public static class LoginDTO {

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;
    }
}
