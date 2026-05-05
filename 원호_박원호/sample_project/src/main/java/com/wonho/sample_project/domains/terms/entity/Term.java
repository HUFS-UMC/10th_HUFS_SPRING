package com.wonho.sample_project.domains.terms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long term_id;

    private String term_name;
}
