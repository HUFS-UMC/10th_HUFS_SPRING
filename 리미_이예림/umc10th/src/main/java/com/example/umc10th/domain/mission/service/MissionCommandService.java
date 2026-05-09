package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.exception.StoreErrorCode;
import com.example.umc10th.domain.mission.exception.StoreException;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionCommandService {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

    // 가게 미션 생성
    public void createMission(Long storeId, MissionReqDTO.CreateMission dto) {

        // 1. storeId로 가게 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        // 2. 컨버터로 Mission 엔티티 생성
        Mission mission = MissionConverter.toMission(store, dto);

        // 3. DB에 저장
        missionRepository.save(mission);
    }
}
