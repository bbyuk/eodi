package com.bb.eodi.deal.application.input;

import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

/**
 * 부동산 매매 데이터 추천 조회 application input
 */
public record FindRecommendedSellInput(
        // 보유 현금
        Long cash,
        // 대상 지역 ID list
        List<Long> targetRegionIds,
        // 대상 주택 유형 list
        List<String> targetHousingTypes,
        // 대출여부
        Boolean hasLoan,
        // 생애최초구매여부
        Boolean isFirstTimeBuyer,
        // 대출 월 상환액 (단위: 만 원)
        Long monthlyPayment,
        // 연소득 (단위: 만 원)
        Long annualIncome,
        // 최대 가격 (만원 단위)
        Long maxPrice,
        // 최소 가격 (만원 단위)
        Long minPrice,
        // 최대전용면적
        Integer maxNetLeasableArea,
        // 최소전용면적
        Integer minNetLeasableArea
) {
}
