package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다.")
    @PostMapping
    public ApiResponse<MemberResDTO.JoinResultDTO> join(@RequestBody @Valid MemberReqDTO.JoinDTO request) {
        return ApiResponse.onSuccess(null);
    }
}
