package com.bb.eodi.deal.infrastructure.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 거래 지역 조회 전용 projection dto
 */
@Getter
public class DealRegionSummaryDto {
    // 지역ID (법정동 기준)
    private final Long regionId;
    // 계약일
    private final LocalDate contractDate;

    @QueryProjection
    public DealRegionSummaryDto(Long regionId, LocalDate contractDate) {
        this.regionId = regionId;
        this.contractDate = contractDate;
    }
}
