package com.wonho.sample_project.domains.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReviewPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_picture_id;

    @ManyToOne
    private Review review;

    private String photo_url;
}
