package com.wonho.sample_project.domains.user.repository;

import com.wonho.sample_project.domains.user.entity.UserMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
    Page<UserMission> findByUser_UserId(Long userId, Pageable pageable);
    Page<UserMission> findByUser_UserIdAndIsCompleted(Long userId, Boolean isCompleted, Pageable pageable);
}

