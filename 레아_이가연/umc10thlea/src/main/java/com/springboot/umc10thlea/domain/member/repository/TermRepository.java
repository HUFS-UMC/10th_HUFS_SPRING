package com.springboot.umc10thlea.domain.member.repository;

import com.springboot.umc10thlea.domain.member.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TermRepository extends JpaRepository<Term, Long> {
    Optional<Term> findByName(String name);

    List<Term> findAllByNameIn(Collection<String> names);
}
