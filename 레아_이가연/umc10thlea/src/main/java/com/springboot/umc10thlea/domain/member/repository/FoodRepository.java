package com.springboot.umc10thlea.domain.member.repository;

import com.springboot.umc10thlea.domain.member.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByName(String name);

    List<Food> findAllByNameIn(Collection<String> names);
}
