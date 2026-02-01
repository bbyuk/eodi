package com.bb.eodi.deal.presentation.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

/**
 * 지역 추천 요청 파라미터
 */
public record RegionRecommendRequest(

        @NotNull(message = "보유 현금은 필수값입니다.")
        @Positive(message = "보유 현금은 0보다 커야합니다.")
        @Parameter(description = "보유 현금 (단위: 만 원)", example = "24000")
        Long cash,

        @Parameter(description = "대출 여부", example = "true")
        Boolean hasLoan,

        @Parameter(description = "생애 최초 구매 여부", example = "true")
        Boolean isFirstTimeBuyer,

        @Parameter(description = "대출 월 상환액 (단위: 만 원)", example = "210")
        Long monthlyPayment,

        @Parameter(description = "연 소득 (단위: 만 원)", example = "5000")
        Long annualIncome,

        @Parameter(description = "대상 주택 유형 목록", example = "AP")
        List<String> housingTypes
) {
}
