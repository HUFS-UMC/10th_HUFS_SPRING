package com.springboot.umc10thlea.domain.mission.service;

import com.springboot.umc10thlea.domain.mission.dto.MissionHomeDetailResDto;
import com.springboot.umc10thlea.domain.mission.dto.MissionHomeResDto;
import com.springboot.umc10thlea.domain.mission.entity.Mission;
import com.springboot.umc10thlea.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;

    public MissionHomeResDto getHomeMissions(Long regionId, Integer page, Integer size) {
        Page<Mission> missionPage = missionRepository.findMissionsByRegionId(regionId, PageRequest.of(page - 1, size));

        List<MissionHomeDetailResDto> missionDetailList = missionPage.getContent().stream()
                .map(m -> MissionHomeDetailResDto.builder()
                        .missionId(m.getId())
                        .title(m.getTitle())
                        .point(m.getPoint())
                        // 예제용 D-Day (실제론 현재 시간 기준 계산 로직 필요)
                        .dDay("D-" + java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.now(), m.getDeadline()))
                        .build())
                .collect(Collectors.toList());

        return MissionHomeResDto.builder()
                .regionName("테스트 지역") // 나중에 Region 조회 추가
                .regionProgress(70)
                .missions(missionDetailList)
                .build();
    }
}