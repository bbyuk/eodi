package com.bb.eodi.deal.application.input;

import com.bb.eodi.deal.domain.type.HousingType;

import java.time.YearMonth;
import java.util.List;

/**
 * 부동산 매매 데이터 조회 application input
 */
public record FindRealEstateSellInput(
        // 최대가격
        Integer maxPrice,
        // 최소가격
        Integer minPrice,
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
