package com.bb.eodi.deal.domain.query;


import com.bb.eodi.deal.domain.type.HousingType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 부동산 거래 발생 지역 데이터 query parameters
 */
@Data
@Builder
public class RegionQuery {
    // 최소예산
    private Long minCash;
    // 최대예산
    private Long maxCash;
    // 시작일자
    private LocalDate startDate;
    // 종료일자
    private LocalDate endDate;
    // 조회대상 주택유형
    private List<HousingType> housingTypes;
    // 지역 추천 조회 최소 거래건수
    private Integer minDealCount;
}
