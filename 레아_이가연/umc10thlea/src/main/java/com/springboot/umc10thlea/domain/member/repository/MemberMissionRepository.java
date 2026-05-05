package com.springboot.umc10thlea.domain.member.repository;

import com.springboot.umc10thlea.domain.member.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    // JOIN FETCH를 사용하여 연관된 미션과 가게 정보를 한 번에 가져와 N+1 문제를 방지합니다.
    @Query("SELECT mm FROM MemberMission mm JOIN FETCH mm.mission m JOIN FETCH m.store s WHERE mm.member.id = :memberId AND mm.status = :status")
    Page<MemberMission> findMyMissionsByStatus(@Param("memberId") Long memberId, @Param("status") String status, Pageable pageable);
}
