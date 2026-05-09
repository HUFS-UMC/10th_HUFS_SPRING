package com.wonho.sample_project.domains.missions.repository;

import com.wonho.sample_project.domains.missions.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query("SELECT m FROM Mission m WHERE m.store.region.region_id = :regionId")
    Page<Mission> findByRegionId(@Param("regionId") Long regionId, Pageable pageable);
}

