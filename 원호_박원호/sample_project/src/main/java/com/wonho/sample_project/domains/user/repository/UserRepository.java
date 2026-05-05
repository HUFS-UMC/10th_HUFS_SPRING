package com.wonho.sample_project.domains.user.repository;

import com.wonho.sample_project.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> getUserBy(Long userId);
}

