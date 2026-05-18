package com.springboot.umc10thlea.domain.member.repository;

import com.springboot.umc10thlea.domain.member.entity.mapping.MemberFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberFoodRepository extends JpaRepository<MemberFood, Long> {
    List<MemberFood> findAllByMemberId(Long memberId);
}
