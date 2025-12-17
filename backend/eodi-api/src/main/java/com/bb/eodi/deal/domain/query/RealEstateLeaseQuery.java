package com.bb.eodi.deal.domain.query;

import com.bb.eodi.deal.domain.type.HousingType;
import lombok.Builder;
import lombok.Data;

import java.time.YearMonth;
import java.util.List;

/**
 * 부동산 임대차 실거래가 데이터 조회 query parameter
 */
@Data
@Builder
public class RealEstateLeaseQuery {

    // 최대 보증금
    private Integer maxDeposit;
    // 최소 보증금
    private Integer minDeposit;
    // 최대 울세
    private Integer maxMonthlyRentFee;
    // 최소 월세
    private Integer minMonthlyRentFee;
    // 최대 전용면적
    private Integer maxNetLeasableArea;
    // 최소 전용면적
    private Integer minNetLeasableArea;
    // 조회 계약년월 시작월
    private YearMonth startYearMonth;
    // 조회 계약년월 종료월
    private YearMonth endYearMonth;
    // 대상 지역 ID 리스트
    private List<Long> targetRegionIds;
    // 대상 주택유형 리스트
    private List<HousingType> targetHousingTypes;
}
