package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {

    REVIEW_CREATED(HttpStatus.OK, "REVIEW2001", "리뷰가 성공적으로 작성되었습니다."),
    REVIEW_FOUND(HttpStatus.OK, "REVIEW2002", "리뷰 목록을 성공적으로 조회하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}