package com.bb.eodi.deal.application.result;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 부동산 임대차 실거래가 domain entity <-> application dto
 */
public record RealEstateLeaseSummaryResult(
        Long id,
        // 대상지역 법정동 ID
        Long regionId,
        // 법정동 명
        String legalDongName,
        // 계약일
        LocalDate contractDate,
        // 계약시작 월
        Integer contractStartMonth,
        // 계약종료 월
        Integer contractEndMonth,
        // 보증금
        Integer deposit,
        // 월세
        Integer monthlyRent,
        // 이전 계약 보증금
        Integer previousDeposit,
        // 이전 계약 월세
        Integer previousMonthlyRent,
        // 연면적
        BigDecimal totalFloorArea,
        // 건축년도
        Integer buildYear,
        // 전용면적
        BigDecimal netLeasableArea,
        // 주택유형
        String housingType,
        // 대상명
        String targetName,
        // 층
        Integer floor,
        // 갱신계약청구권 사용
        boolean useRRRight
) {
}
