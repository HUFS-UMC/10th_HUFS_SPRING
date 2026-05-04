package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.mapping.MemberMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    public Page<Mission> getMissionsByLocation(String locationName, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return missionRepository.findMissionsByLocationName(locationName, pageable);
    }

    // Enum 대신 Boolean isComplete 사용
    public Page<MemberMission> getMyMissionsByComplete(Long memberId, Boolean isComplete, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return memberMissionRepository.findMyMissions(memberId, isComplete, pageable);
    }
}