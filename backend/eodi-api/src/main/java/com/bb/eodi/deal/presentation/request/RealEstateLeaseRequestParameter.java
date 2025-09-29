package com.bb.eodi.deal.presentation.request;

import com.bb.eodi.deal.domain.type.HousingType;

import java.util.List;

/**
 * 부동산 임대차 실거래가 데이터 요청 파라미터
 */
public record RealEstateLeaseRequestParameter(
        Integer maxDeposit,
        Integer minDeposit,
        Integer maxMonthlyRentFee,
        Integer minMonthlyRentFee,
        Integer maxNetLeasableArea,
        Integer minNetLeasableArea,
        List<Long> targetRegionIds,
        List<HousingType> targetHousingTypes
) {
}
