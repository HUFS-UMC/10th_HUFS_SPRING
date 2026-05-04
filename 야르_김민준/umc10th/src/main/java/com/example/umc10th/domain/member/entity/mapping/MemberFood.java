package com.example.umc10th.domain.member.entity.mapping;

import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member_food")
public class MemberFood extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 나는 N(다)이고, Member는 1(일)이다.
    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩
    @JoinColumn(name = "member_id") //FK의 주인설정
    private Member member;

    // 나는 N(다)이고, Food는 1(일)이다.
    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩
    @JoinColumn(name = "food_id") //FK의 주인설정
    private Food food;
}