package com.bb.eodi.deal.application.input;

import com.bb.eodi.deal.domain.type.HousingType;

import java.util.List;

/**
 * 부동산 임대차 거래 추천 조회 application input
 * @param cash 보유 현금
 * @param targetRegionIds 대상 지역 ID 목록
 * @param targetHousingTypes 대상 주택 유형 목록
 * @param maxDeposit 최대 보증금
 * @param minDeposit 최소 보증금
 * @param maxMonthlyRentFee 최대 월세
 * @param minMonthlyRentFee 최소 월세
 * @param maxNetLeasableArea 최대 전용면적
 * @param minNetLeasableArea 최소 전용면적
 */
public record FindRecommendedLeaseInput(
        
        // 보유현금
        Integer cash,
        // 대상 지역 ID 목록
        List<Long> targetRegionIds,
        // 대상 주택 유형 목록
        List<HousingType> targetHousingTypes,
        // 최대 보증금
        Integer maxDeposit,
        // 최소 보증금
        Integer minDeposit,
        // 최대 월세
        Integer maxMonthlyRentFee,
        // 최소 월세
        Integer minMonthlyRentFee,
        // 최대 전용면적
        Integer maxNetLeasableArea,
        // 최소 전용면적
        Integer minNetLeasableArea
) {
}
