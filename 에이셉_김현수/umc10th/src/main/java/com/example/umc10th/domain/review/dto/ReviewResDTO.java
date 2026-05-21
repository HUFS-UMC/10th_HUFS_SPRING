package com.example.umc10th.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResultDTO {
        Long reviewId;
        LocalDateTime createdAt;
    }

    // 리뷰 단건 미리보기 — 오프셋/커서 공통 사용
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPreviewDTO {
        Long reviewId;
        String storeName;
        String body;
        Float star;
        LocalDateTime createdAt;
    }

    // [5주차] 오프셋 기반 리뷰 목록 래퍼 (Page)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPreviewListDTO {
        List<ReviewPreviewDTO> reviewList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    // [7주차] 커서 기반 리뷰 목록 래퍼 (Slice)
    // totalPage/totalElements 제거 → hasNext + nextCursor로 대체
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CursorReviewListDTO {
        List<ReviewPreviewDTO> reviewList;
        Integer listSize;
        Boolean hasNext;       // 다음 페이지 존재 여부 (Slice.hasNext())
        String nextCursor;     // 다음 요청에 쓸 커서값, 마지막 페이지면 null
    }
}