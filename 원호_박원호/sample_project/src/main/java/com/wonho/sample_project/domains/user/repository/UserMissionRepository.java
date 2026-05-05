package com.wonho.sample_project.domains.user.repository;

import com.wonho.sample_project.domains.user.entity.User;
import com.wonho.sample_project.domains.user.entity.UserMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
    Page<UserMission> findByUser(User user, Pageable pageable);
    Page<UserMission> findByUserAndCompleted(User user, Boolean completed, Pageable pageable);
}

