package com.springboot.umc10thlea.domain.mission.repository;

import com.springboot.umc10thlea.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query("SELECT m FROM Mission m JOIN FETCH m.store s WHERE s.region.id = :regionId")
    Page<Mission> findMissionsByRegionId(@Param("regionId") Long regionId, Pageable pageable);

    List<Mission> findAllByStore_Id(Long storeId);
}
