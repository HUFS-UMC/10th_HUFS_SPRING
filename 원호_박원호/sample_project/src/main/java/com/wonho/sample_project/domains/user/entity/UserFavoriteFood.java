package com.wonho.sample_project.domains.user.entity;

import com.wonho.sample_project.domains.food.entity.Food;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserFavoriteFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_food_id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Food food_id;
}
