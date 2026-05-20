package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    // API 4: 홈 화면 — 해당 지역 가게의 미션 중 이 회원이 아직 도전하지 않은 것만 조회
    // @ManyToOne 체인(m→store→location)이므로 JOIN FETCH + Pageable 조합이 안전함 (컬렉션 아님)
    // countQuery를 분리하여 페이징 정확도 보장
    @Query(
        value = "SELECT m FROM Mission m "
              + "JOIN FETCH m.store s "
              + "JOIN FETCH s.location l "
              + "WHERE s.location.id = :locationId "
              + "AND m.id NOT IN ("
              + "  SELECT mm.mission.id FROM MemberMission mm WHERE mm.member.id = :memberId"
              + ") "
              + "ORDER BY m.createdAt DESC",
        countQuery = "SELECT COUNT(m) FROM Mission m "
                   + "JOIN m.store s "
                   + "WHERE s.location.id = :locationId "
                   + "AND m.id NOT IN ("
                   + "  SELECT mm.mission.id FROM MemberMission mm WHERE mm.member.id = :memberId"
                   + ")"
    )
    Page<Mission> findAvailableMissionsByLocation(
        @Param("locationId") Long locationId,
        @Param("memberId") Long memberId,
        Pageable pageable
    );
}
