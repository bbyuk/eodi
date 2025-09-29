package com.bb.eodi.deal.presentation.request;

import com.bb.eodi.deal.domain.type.HousingType;

import java.time.YearMonth;
import java.util.List;

/**
 * 부동산 매매 실거래가 데이터 요청 파라미터
 */
public record RealEstateSellRequestParameter(
        // 최대 가격
        Integer maxPrice,
        // 최소 가격
        Integer minPrice,
        // 조회기간 시작월
        YearMonth startYearMonth,
        // 조회기간 종료월
        YearMonth endYearMonth,
        // 대상 지역 ID 리스트
        List<Long> targetRegionIds,
        // 대상 주택유형 리스트
        List<HousingType> targetHousingTypes
) {
}
