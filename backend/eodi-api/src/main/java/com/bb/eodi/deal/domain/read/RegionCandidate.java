package com.bb.eodi.deal.domain.read;

import com.bb.eodi.deal.domain.type.HousingType;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

/**
 * 부동산 실거래가 지역 조회 필터 대상 후보자 read model
 */
public record RegionCandidate(
        // 지역 ID
        Long regionId,
        // 가격
        Long price,
        // 주택유형
        HousingType housingType,
        // 계약일자
        LocalDate contractDate
) {
    @QueryProjection
    public RegionCandidate {}
}
