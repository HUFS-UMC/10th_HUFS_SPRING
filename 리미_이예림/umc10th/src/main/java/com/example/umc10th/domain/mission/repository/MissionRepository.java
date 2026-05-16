package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 커서 없이 조회 (첫 페이지)
    Slice<Mission> findMissionsByStore_IdOrderByIdDesc(Long storeId, Pageable pageable);

    // 커서 있을 때 조회 (id < idCursor)
    Slice<Mission> findMissionsByStore_IdAndIdLessThanOrderByIdDesc(Long storeId, Long idCursor, Pageable pageable);
}
