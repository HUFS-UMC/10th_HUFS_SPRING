package com.wonho.sample_project.domains.user.entity;

import com.wonho.sample_project.domains.terms.entity.Term;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserTerms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_terms_id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Term term;
}
