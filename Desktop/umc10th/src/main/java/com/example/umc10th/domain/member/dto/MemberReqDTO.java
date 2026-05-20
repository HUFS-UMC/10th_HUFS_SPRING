package com.example.umc10th.domain.member.dto;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    //마이페이지
    public record GetInfo(
            Long id
    ){}

    //회원가입
    public record SignUpDTO(
            AgreeDTO agree,
            String name,
            String gender,
            LocalDate birth,
            String address,
            String detailAddress,
            List<String> foodList,
            String email,
            String password
    ) {}

    public record AgreeDTO(
            Boolean age,
            Boolean service,
            Boolean privacy,
            Boolean location,
            Boolean marketing
    ) {}
}
