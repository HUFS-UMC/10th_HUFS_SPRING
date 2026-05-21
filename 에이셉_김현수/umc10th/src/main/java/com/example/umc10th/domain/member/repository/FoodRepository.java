package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findAllByIdIn(List<Long> ids);
}
