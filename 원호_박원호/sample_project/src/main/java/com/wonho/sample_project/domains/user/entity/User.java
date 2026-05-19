package com.wonho.sample_project.domains.user.entity;


import com.wonho.sample_project.domains.user.enums.Address;
import com.wonho.sample_project.domains.user.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String name;
    private String password;
    private Gender gender;
    private LocalDate birth;
    private Address address;
    private String detail_address;
    private String social_uid;
    private String social_type;
    private Integer point;
    private String email;
    private String phone_number;
    private LocalDate created_at;
    private LocalDate updated_at;
    private LocalDate deleted_at;
}
