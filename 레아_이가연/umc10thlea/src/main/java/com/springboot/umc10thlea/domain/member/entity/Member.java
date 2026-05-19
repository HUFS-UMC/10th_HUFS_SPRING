package com.springboot.umc10thlea.domain.member.entity;

import com.springboot.umc10thlea.domain.member.enums.Gender;
import com.springboot.umc10thlea.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    private LocalDate birth;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(length = 255)
    private String detailAddress;

    @Column(nullable = false)
    @Builder.Default
    private Integer point = 0;
}
