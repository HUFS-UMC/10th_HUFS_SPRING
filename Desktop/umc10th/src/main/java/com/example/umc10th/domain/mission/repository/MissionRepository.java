package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission,Long> {
    List<Mission> findAllByStore_Id(Long storeId);
    // 커서 없이 조회
    Slice<Mission> findMissionsByStore_IdOrderByIdDesc(Long storeId, Pageable pageable);

    // 커서 기반 조회
    Slice<Mission> findMissionByStore_IdAndIdLessThanOrderByIdDesc(Long storeId, Long idCursor, Pageable pageable);
}
