package com.wonho.sample_project.domains.missions.service;

import com.wonho.sample_project.domains.missions.dto.MissionRequestDTO;
import com.wonho.sample_project.domains.missions.entity.Mission;
import com.wonho.sample_project.domains.missions.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;

    /**
     * Region 별로 존재하는 미션을 불러오기 (페이지네이션 + Region 필터)
     */
    public MissionRequestDTO.GetMissions getMissionsByRegion(Long regionId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Mission> missionPage;

        if (regionId != null) {
            missionPage = missionRepository.findByRegionId(regionId, pageable);
        } else {
            missionPage = missionRepository.findAll(pageable);
        }

        List<MissionRequestDTO.GetMissions.MissionInfo> missionInfos = missionPage.getContent().stream()
                .map(mission -> MissionRequestDTO.GetMissions.MissionInfo.builder()
                        .missionId(mission.getMission_id())
                        .storeName(mission.getStore().getName())
                        .regionName(mission.getStore().getRegion().getName())
                        .conditional(mission.getConditional())
                        .point(mission.getPoint())
                        .deadline(mission.getDeadline())
                        .build())
                .collect(Collectors.toList());

        return MissionRequestDTO.GetMissions.builder()
                .missions(missionInfos)
                .page(missionPage.getNumber())
                .size(missionPage.getSize())
                .totalElements(missionPage.getTotalElements())
                .totalPages(missionPage.getTotalPages())
                .hasNext(missionPage.hasNext())
                .build();
    }
}

