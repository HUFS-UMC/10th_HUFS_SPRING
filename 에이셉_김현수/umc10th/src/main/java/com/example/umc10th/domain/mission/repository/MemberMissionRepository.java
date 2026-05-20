package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 동일한 미션에 이미 도전 중인지 확인 (중복 도전 방지)
    boolean existsByMemberIdAndMissionId(Long memberId, Long missionId);

    // API 3: 내 미션 목록 — JOIN FETCH로 N+1 방지, countQuery 분리로 페이징 정확도 보장
    @Query(
        value = "SELECT mm FROM MemberMission mm "
              + "JOIN FETCH mm.mission m "
              + "JOIN FETCH m.store s "
              + "WHERE mm.member.id = :memberId "
              + "AND mm.status = :status "
              + "ORDER BY mm.createdAt DESC",
        countQuery = "SELECT COUNT(mm) FROM MemberMission mm "
                   + "WHERE mm.member.id = :memberId "
                   + "AND mm.status = :status"
    )
    Page<MemberMission> findAllByMemberIdAndStatusWithMission(
        @Param("memberId") Long memberId,
        @Param("status") MissionStatus status,
        Pageable pageable
    );
}
