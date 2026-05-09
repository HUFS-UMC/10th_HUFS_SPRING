package com.wonho.sample_project.domains.review.entity;

import com.wonho.sample_project.domains.store.entity.Store;
import com.wonho.sample_project.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    private String content;
    private LocalDate created_at;
    private Integer star;
    @ManyToOne
    private User user;

    @ManyToOne
    private Store store;

    @ManyToOne
    private ReviewReply reply;
}
