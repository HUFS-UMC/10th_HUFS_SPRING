package com.example.umc10th.domain.mission.service;


import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.StoreException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.exception.code.StoreErrorCode;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

    public void createMission(Long storeId, MissionReqDTO.CreateMission dto) {

        //가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        //미션 생성
        Mission mission = MissionConverter.toMission(store, dto);

        //미션 DB 저장
        missionRepository.save(mission);
    }

    //가게 내 미션들 조회
    public MissionResDTO.Pagination<MissionResDTO.GetMission> getMissions(
            Long storeId,
            Integer pageSize,
            String cursor,
            String query
    ){

        //페이지 정보들을 PageRequest로 만들기
        PageRequest pageRequest = PageRequest.of(0,pageSize);

        long idCursor;
        Slice<Mission> missionList;
        String nextCursor;

        //커서가 있는 경우
        if(!cursor.equals("-1")){

            //커서 분리
            String[] cursorSplit = cursor.split(":");
            switch (query.toLowerCase()){
                case "id":

                    //커서 타입 변환
                    Long prevCursor = Long.parseLong(cursorSplit[0]);
                    idCursor=Long.parseLong(cursorSplit[1]);

                    //가게 내 미션들 조회 & where절에 커서값 기입
                    missionList = missionRepository.findMissionByStore_IdAndIdLessThanOrderByIdDesc(
                            storeId,
                            idCursor,
                            pageRequest
                    );
                    break;

                default:
                    throw new MissionException(MissionErrorCode.QUERY_NOT_VALID.getMessage());
            }



        }else{
            //커서 없이 조회
            missionList= missionRepository.findMissionsByStore_IdOrderByIdDesc(storeId,pageRequest);
        }

        //다음 커서 계산
        List<Mission> content = missionList.getContent();

        nextCursor = "-1";

        if(!content.isEmpty()){

            Mission lastMission = content.get(content.size()-1);

            nextCursor = lastMission.getId() + ":" + lastMission.getId();
        }

        //미션들 응답 DTO로 포장하기
        return MissionConverter.toPagination(
                missionList.stream()
                        .map(MissionConverter::toGetMission)
                        .toList(),
                missionList.hasNext(),
                nextCursor,
                missionList.getSize()

        );
    }
}
