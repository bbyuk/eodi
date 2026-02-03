package com.bb.eodi.deal.domain.query;

import com.bb.eodi.deal.domain.type.HousingType;
import lombok.Builder;
import lombok.Data;

import java.time.YearMonth;
import java.util.List;

/**
 * 부동산 매매 실거래가 데이터 조회 query parameters
 */
@Data
@Builder
public class RealEstateSellQuery {
    // 최대 가격
    private Long maxPrice;
    // 최소 가격
    private Long minPrice;
    // 최대 전용 면적
    private Integer maxNetLeasableArea;
    // 최소 전용 면적
    private Integer minNetLeasableArea;
    // 조회기간 시작월
    private YearMonth startYearMonth;
    // 조회기간 종료월
    private YearMonth endYearMonth;
    // 대상 지역 ID 리스트
    private List<Long> targetRegionIds;
    // 대상 주택유형 리스트
    private List<HousingType> targetHousingTypes;
}
