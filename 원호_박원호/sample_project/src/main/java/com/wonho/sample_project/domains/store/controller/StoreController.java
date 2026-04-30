package com.wonho.sample_project.domains.store.controller;

import com.wonho.sample_project.domains.store.dto.StoreRequestDTO;
import com.wonho.sample_project.domains.user.dto.UserRequestDTO;
import com.wonho.sample_project.global.api.ApiResponse;
import com.wonho.sample_project.global.api.code.BaseSuccessCode;
import com.wonho.sample_project.global.api.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
public class StoreController {


    @PostMapping("/:storeId/review")
    public ApiResponse<StoreRequestDTO.CreateReview> getReview() {
        BaseSuccessCode code = GeneralSuccessCode.OK;

        // Todo: result 에 payload 넣기
        return ApiResponse.onSuccess(code,null);
    };
}
