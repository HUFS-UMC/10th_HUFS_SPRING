package com.springboot.umc10thlea.domain.member.repository;

import com.springboot.umc10thlea.domain.member.entity.mapping.MemberTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberTermRepository extends JpaRepository<MemberTerm, Long> {
    List<MemberTerm> findAllByMemberId(Long memberId);
}
