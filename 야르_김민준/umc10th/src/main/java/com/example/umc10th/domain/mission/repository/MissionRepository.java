package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 홈 화면: 특정 지역(Location) 이름으로 미션 목록 조회
    @Query("SELECT m FROM Mission m JOIN m.store s JOIN s.location l WHERE l.name = :locationName")
    Page<Mission> findMissionsByLocationName(@Param("locationName") String locationName, Pageable pageable);
}