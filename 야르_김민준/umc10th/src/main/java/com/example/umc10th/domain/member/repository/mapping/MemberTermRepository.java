package com.example.umc10th.domain.member.repository.mapping;

import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTermRepository extends JpaRepository<MemberTerm, Long> {
}
