package com.wonho.sample_project.domains.user.entity;

import com.wonho.sample_project.domains.missions.entity.Mission;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_mission_id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Mission mission;

    private Boolean is_completed;
}
