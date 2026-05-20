package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.NONE;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "address", nullable = false)
    @Enumerated(EnumType.STRING)
    private Address address;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "social_uid", nullable = false)
    private String socialUid;

    @Column(name = "social_type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(nullable = false)
    private Integer point;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 11)
    private String phoneNumber;

    @Column(name = "profile_url", columnDefinition = "TEXT")
    private String profileUrl;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // --- 양방향 매핑 설정 ---

    // 사용자 - 약관 동의 내역 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<MemberTerm> memberTermList = new ArrayList<>();

    // 사용자 - 선호 음식 카테고리 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<MemberFood> memberFoodList = new ArrayList<>();

    // 사용자 - 진행 중인 미션 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<MemberMission> memberMissionList = new ArrayList<>();

    // 사용자 - 작성한 리뷰 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Review> reviewList = new ArrayList<>();
}