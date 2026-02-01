package com.bb.eodi.deal.application.input;

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
