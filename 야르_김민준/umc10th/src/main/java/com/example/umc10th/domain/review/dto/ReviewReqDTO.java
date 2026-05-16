package com.example.umc10th.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

public class ReviewReqDTO {
    @Getter
    @Setter
    public static class ReviewCreateDTO {
        @NotNull(message = "사용자 ID는 필수 입력 항목입니다.")
        private Long memberId;

        @NotNull(message = "가게 ID는 필수 입력 항목입니다.")
        private Long storeId;

        @NotNull(message = "별점은 필수 입력 항목입니다.")
        @DecimalMin(value = "0.0", message = "별점은 0.0 이상이어야 합니다.")
        @DecimalMax(value = "5.0", message = "별점은 5.0 이하여야 합니다.")
        private Float star;

        @NotBlank(message = "리뷰 내용은 필수 입력 항목입니다.")
        @Size(min = 1, max = 200, message = "리뷰 내용은 1자 이상 200자 이하로 작성해야 합니다.")
        private String content;
    }

    @Getter
    public static class MyReviewListReqDTO {
        @NotNull(message = "사용자 ID는 필수 입력 항목입니다.")
        @Schema(description = "사용자 ID", example = "1")
        private Long memberId;
        @Schema(description = "ID 커서 (ID 순 정렬 시 사용)", example = "10")
        private Long cursorId;
        @Schema(description = "별점 커서 (별점 순 정렬 시 사용)", example = "4.5")
        private Float cursorStar;
    }
}