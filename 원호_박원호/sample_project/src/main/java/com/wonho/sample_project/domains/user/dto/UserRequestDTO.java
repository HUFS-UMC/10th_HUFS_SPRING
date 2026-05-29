package com.wonho.sample_project.domains.user.dto;

import com.wonho.sample_project.domains.user.entity.UserMission;
import com.wonho.sample_project.domains.user.enums.Address;
import com.wonho.sample_project.domains.user.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class UserRequestDTO {

    @RequiredArgsConstructor
    @Getter
    public static class CreateUser {
        @NotNull
        private String name;

        @NotNull
        private String password;

        @NotNull
        private Gender gender;

        @NotNull
        private LocalDate birth;

        @NotNull
        private Address address;

        @NotNull
        private String detailed_address;

        @NotNull
        private String email;

        @NotNull
        private String phone_number;
    };

    @AllArgsConstructor
    @Builder
    @Getter
    public static class GetUser {
        private Long userId;
        private String name;
        private Gender gender;
        private LocalDate birth;
        private Address address;
        private String detailAddress;
        private String email;
        private String phoneNumber;
        private Integer point;
    };

    @AllArgsConstructor
    @Builder
    @Getter
    public static class LoginUser{
        private String accessToken;
    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class GetMission {
        private List<MissionInfo> missions;
        private Integer page;
        private Integer size;
        private Long totalElements;
        private Integer totalPages;
        private Boolean hasNext;

        @AllArgsConstructor
        @Builder
        @Getter
        public static class MissionInfo {
            private Long missionId;
            private String storeName;
            private String conditional;
            private Integer point;
            private LocalDate deadline;
            private Boolean isCompleted;

            public static MissionInfo fromUserMission(UserMission userMission){
                return MissionInfo.builder()
                        .missionId(userMission.getMission().getMission_id())
                        .storeName(userMission.getMission().getStore().getName())
                        .conditional(userMission.getMission().getConditional())
                        .point(userMission.getMission().getPoint())
                        .deadline(userMission.getMission().getDeadline())
                        .isCompleted(userMission.getCompleted())
                        .build();
            }
        }
    };

    @RequiredArgsConstructor
    @Getter
    public static class UpdateMissionComplete {
        private final Boolean complete;
    };
}
