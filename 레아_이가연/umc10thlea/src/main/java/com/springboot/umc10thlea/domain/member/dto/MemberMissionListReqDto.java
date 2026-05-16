package com.springboot.umc10thlea.domain.member.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberMissionListReqDto {
    @NotNull
    private Long userId;

    @NotNull
    @Min(0)
    private Integer pageNumber;

    @NotNull
    @Min(1)
    private Integer pageSize;
}
