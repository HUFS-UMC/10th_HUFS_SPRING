package com.wonho.sample_project.domains.user.service;

import com.wonho.sample_project.domains.user.dto.UserRequestDTO;
import com.wonho.sample_project.domains.user.entity.User;
import com.wonho.sample_project.domains.user.entity.UserMission;
import com.wonho.sample_project.domains.user.repository.UserMissionRepository;
import com.wonho.sample_project.domains.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;

    public User createUser(UserRequestDTO.CreateUser user) {
        User newUser = User.builder()
                .birth(user.getBirth())
                .point(0)
                .address(user.getAddress())
                .detail_address(user.getDetailed_address())
                .email(user.getEmail())
                .name(user.getName())
                .phone_number(user.getPhone_number())
                .gender(user.getGender())
                .build();

        return userRepository.save(newUser);
    }

    public UserRequestDTO.GetUser getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        return UserRequestDTO.GetUser.builder()
                .userId(user.getUser_id())
                .name(user.getName())
                .gender(user.getGender())
                .point(user.getPoint())
                .birth(user.getBirth())
                .phoneNumber(user.getPhone_number())
                .detailAddress(user.getDetail_address())
                .address(user.getAddress())
                .email(user.getEmail())
                .build();
    }

    /**
     * 유저별로 미션 확인 (페이지네이션 + 진행여부 필터)
     */
    public UserRequestDTO.GetMission getUserMissions(Long userId, Boolean isCompleted, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserMission> userMissionPage;
        User user = userRepository.findById(userId).orElseThrow();

        if (isCompleted != null) {
            userMissionPage = userMissionRepository.findByUserAndCompleted(user, isCompleted, pageable);
        } else {
            userMissionPage = userMissionRepository.findByUser(user, pageable);
        }

        List<UserRequestDTO.GetMission.MissionInfo> missionInfos = userMissionPage.getContent().stream()
                .map(UserRequestDTO.GetMission.MissionInfo::fromUserMission)
                .collect(Collectors.toList());

        return UserRequestDTO.GetMission.builder()
                .missions(missionInfos)
                .page(userMissionPage.getNumber())
                .size(userMissionPage.getSize())
                .totalElements(userMissionPage.getTotalElements())
                .totalPages(userMissionPage.getTotalPages())
                .hasNext(userMissionPage.hasNext())
                .build();
    }

    /**
     * 미션 완료 처리
     */
    @Transactional
    public UserRequestDTO.UpdateMissionComplete completeMission(Long userId, Long missionId) {
        UserMission userMission = userMissionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("UserMission not found with id: " + missionId));

        if (!userMission.getUser().getUser_id().equals(userId)) {
            throw new RuntimeException("This mission does not belong to the user");
        }

        userMission.setCompleted(true);
        userMissionRepository.save(userMission);

        return new UserRequestDTO.UpdateMissionComplete(true);
    }
}
