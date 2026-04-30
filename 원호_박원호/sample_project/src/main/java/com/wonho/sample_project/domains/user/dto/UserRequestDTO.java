package com.wonho.sample_project.domains.user.dto;

import com.wonho.sample_project.domains.user.enums.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

public class UserRequestDTO {
    @RequiredArgsConstructor
    @Getter
    public static class CreateUser {
        private final String name;
        private final Gender gender;
        private final LocalDate birth;
        private final String address;
        private final String detailed_address;
        private final String email;
        private final String phone_number;
    };

    public static class GetUser {};
    public static class GetMission {};

    @RequiredArgsConstructor
    @Getter
    public static class UpdateMissionComplete {
        private final Boolean complete;
    };
}
