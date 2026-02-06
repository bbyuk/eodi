package com.bb.eodi.deal.presentation.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * 부동산 매매 실거래가 추천 데이터 요청 파라미터
 */
public record RealEstateSellRecommendRequestParameter(
        @Parameter(description = "보유 현금", example = "70000", required = true)
        @NotNull(message = "보유 현금은 필수값입니다.")
        @Positive(message = "보유 현금은 0보다 커야합니다.")
        Long cash,

        @Parameter(
                description = "조회 대상 지역 ID 목록",
                example = "[14283, 16122]",
                required = true,
                array = @ArraySchema(
                        schema = @Schema(
                                type = "integer",
                                format = "int64",
                                example = "14283"
                        )
                )
        )
        List<Long> targetRegionIds,
        @Parameter(
                description = "조회 대상 주택 유형 목록",
                example = "[]",
                array = @ArraySchema(
                        schema = @Schema(
                                type = "string",
                                allowableValues = {"AP", "MH", "DT", "MU", "OF", "PR", "OR", "O"}
                        )
                )
        )
        List<String> targetHousingTypes,

        @Parameter(description = "대출 여부", example = "true")
        Boolean hasLoan,

        @Parameter(description = "생애 최초 구매 여부", example = "true")
        Boolean isFirstTimeBuyer,

        @Parameter(description = "대출 월 상환액 (단위: 만 원)", example = "210")
        Long monthlyPayment,

        @Parameter(description = "연 소득 (단위: 만 원)", example = "5000")
        Long annualIncome,

        /**
         * ============ 필터 조건 ============
         */
        @Parameter(description = "최대 가격 (단위: 만원)", example = "34000")
        Long maxPrice,
        @Parameter(description = "최소 가격 (단위: 만원)", example = "24000")
        Long minPrice,
        @Parameter(description = "최대 전용면적", example = "84")
        Integer maxNetLeasableArea,
        @Parameter(description = "최소 전용면적", example = "39")
        Integer minNetLeasableArea
) {
}
