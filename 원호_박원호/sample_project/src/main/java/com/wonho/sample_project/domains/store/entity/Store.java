package com.wonho.sample_project.domains.store.entity;


import com.wonho.sample_project.domains.region.entity.Region;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long store_id;

    private String name;
    private Long manager_number;
    private String detail_address;

    @ManyToOne
    private Region region;
}
