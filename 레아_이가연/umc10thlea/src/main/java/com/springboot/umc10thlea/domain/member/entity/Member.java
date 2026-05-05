package com.springboot.umc10thlea.domain.member.entity;

import com.springboot.umc10thlea.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    @Builder.Default
    private Integer point = 0;
}