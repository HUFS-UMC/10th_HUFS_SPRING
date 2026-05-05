package com.springboot.umc10thlea.domain.member.entity.mapping;

import com.springboot.umc10thlea.domain.member.entity.Member;
import com.springboot.umc10thlea.domain.mission.entity.Mission;
import com.springboot.umc10thlea.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberMission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Column(nullable = false, length = 20)
    private String status; // "CHALLENGING", "COMPLETE" 등
}
