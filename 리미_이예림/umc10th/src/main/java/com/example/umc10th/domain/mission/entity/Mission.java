package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mission")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "point")
    private Integer point;

    @Column(name = "conditional")
    private String conditional;
}
