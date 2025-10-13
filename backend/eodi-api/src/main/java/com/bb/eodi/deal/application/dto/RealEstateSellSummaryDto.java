package com.bb.eodi.deal.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 부동산 매매 데이터
 */
public record RealEstateSellSummaryDto(
        Long id,
        Long regionId,
        String legalDongName,
        LocalDate contractDate,
        Long price,
        Integer buildYear,
        BigDecimal netLeasableArea,
        BigDecimal landArea,
        BigDecimal totalFloorArea,
        String housingType,
        LocalDate dateOfRegistration,
        String targetName,
        String buildingDong,
        Integer floor
) {
}
