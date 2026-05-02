package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // JpaRepository<엔티티 타입, PK 타입>
    // 기본 메서드: save(), findById(), findAll(), delete() 등 자동 제공

    // 커스텀 메서드 예시 (필요할 때 추가)
    // Optional<Member> findByEmail(String email);
}
