package com.springboot.umc10thlea.domain.member.repository;

import com.springboot.umc10thlea.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}