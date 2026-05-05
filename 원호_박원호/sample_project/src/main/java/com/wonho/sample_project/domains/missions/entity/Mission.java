package com.wonho.sample_project.domains.missions.entity;


import com.wonho.sample_project.domains.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mission_id;

    private LocalDate deadline;
    private String conditional;
    private Integer point;
    private LocalDate created_at;
    private LocalDate updated_at;
    private LocalDate deleted_at;

    @ManyToOne
    private Store store;

}
