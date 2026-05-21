package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;

    // 5주차 API: 가게에 미션 등록
    @Transactional
    public MissionResDTO.CreateMissionResultDTO createMission(Long storeId, MissionReqDTO.CreateMissionDTO dto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        Mission mission = MissionConverter.toMission(dto, store);
        missionRepository.save(mission);
        return MissionConverter.toCreateMissionResultDTO(mission);
    }

    // 5주차 API: 미션 도전하기 (MemberMission 생성)
    @Transactional
    public MissionResDTO.ChallengeMissionResultDTO challengeMission(Long missionId, MissionReqDTO.ChallengeMissionDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        if (memberMissionRepository.existsByMemberIdAndMissionId(member.getId(), mission.getId())) {
            throw new MissionException(MissionErrorCode.MISSION_ALREADY_CHALLENGED);
        }

        MemberMission memberMission = MissionConverter.toMemberMission(member, mission);
        memberMissionRepository.save(memberMission);
        return MissionConverter.toChallengeMissionResultDTO(memberMission);
    }

    // API 3: 내 미션 목록 조회 (진행중 / 완료 상태별 페이징)
    public MissionResDTO.MissionPreviewListDTO getMyMissions(Long memberId, MissionStatus status, Integer page) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        // JOIN FETCH로 MemberMission→Mission→Store N+1 방지, countQuery 분리
        Page<MemberMission> resultPage = memberMissionRepository.findAllByMemberIdAndStatusWithMission(
                memberId, status, PageRequest.of(page, 10)
        );
        return MissionConverter.toMissionPreviewListDTO(resultPage);
    }

    // API 4: 홈 화면 — 선택된 지역의 도전 가능한 미션 목록 페이징
    public MissionResDTO.HomeMissionPreviewListDTO getHomeMissions(Long memberId, Long locationId, Integer page) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        // JPQL 다중 조건: 지역 필터 + 미도전 미션 필터, JOIN FETCH로 N+1 방지
        Page<Mission> missionPage = missionRepository.findAvailableMissionsByLocation(
                locationId, memberId, PageRequest.of(page, 10)
        );
        return MissionConverter.toHomeMissionPreviewListDTO(missionPage);
    }
}
