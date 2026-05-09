package com.example.umc10th.domain.mission.repository.mapping;

import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // status(Enum) 대신 ERD에 있는 Boolean isComplete로 조회
    @Query("SELECT mm FROM MemberMission mm JOIN FETCH mm.mission WHERE mm.member.id = :memberId AND mm.isComplete = :isComplete")
    Page<MemberMission> findMyMissions(@Param("memberId") Long memberId, @Param("isComplete") Boolean isComplete, Pageable pageable);
}