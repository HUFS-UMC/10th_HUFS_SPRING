package com.springboot.umc10thlea.domain.mission.service;

import com.springboot.umc10thlea.domain.mission.converter.MissionConverter;
import com.springboot.umc10thlea.domain.mission.dto.MissionHomeDetailResDto;
import com.springboot.umc10thlea.domain.mission.dto.MissionHomeResDto;
import com.springboot.umc10thlea.domain.mission.dto.MissionReqDto;
import com.springboot.umc10thlea.domain.mission.dto.MissionResDto;
import com.springboot.umc10thlea.domain.mission.entity.Mission;
import com.springboot.umc10thlea.domain.mission.entity.Store;
import com.springboot.umc10thlea.domain.mission.exception.StoreException;
import com.springboot.umc10thlea.domain.mission.exception.code.StoreErrorCode;
import com.springboot.umc10thlea.domain.mission.repository.MissionRepository;
import com.springboot.umc10thlea.domain.mission.repository.StoreRepository;
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
    private final StoreRepository storeRepository;

    //가게 미션 생성
    @Transactional
    public Void createMission(Long storeId, MissionReqDto.CreateMission dto) {
        //가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        //미션 생성
        Mission mission = MissionConverter.toMission(store, dto);

        //미션 DB 저장
        missionRepository.save(mission);
        return null;
    }

    //가게 내 미션들 조회
    public List<MissionResDto.GetMission> getMissions(
            Long storeId
    ){
        //가게 내 미션들 조회
        List<Mission> missionList = missionRepository.findAllByStore_Id(storeId);

        //미션들 응답 DTO로 포장하기
        return missionList.stream()
                .map(MissionConverter::toGetMission)
                .toList();

    }


    //홈 화면 (미션 조회)
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
