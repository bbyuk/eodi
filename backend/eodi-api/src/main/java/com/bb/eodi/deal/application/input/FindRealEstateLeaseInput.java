package com.bb.eodi.deal.application.input;

import com.bb.eodi.deal.domain.type.HousingType;

import java.time.YearMonth;
import java.util.List;

/**
 * 부동산 임대차 실거래가 조회 application input
 */
public record FindRealEstateLeaseInput(
        // 최대보증금
        Integer maxDeposit,
        // 최소보증금
        Integer minDeposit,
        // 최대월세
        Integer maxMonthlyRentFee,
        // 최소월세
        Integer minMonthlyRentFee,
        // 최대전용면적
        Integer maxNetLeasableArea,
        // 최소전용면적
        Integer minNetLeasableArea,
        // 시작년월
        YearMonth startYearMonth,
        // 종료년월
        YearMonth endYearMonth,
        // 대상 지역 ID 목록
        List<Long> targetRegionIds,
        // 대상 주택유형 목록
        List<HousingType> targetHousingTypes
) {
}
