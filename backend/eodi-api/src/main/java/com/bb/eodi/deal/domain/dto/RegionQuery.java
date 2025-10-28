package com.bb.eodi.deal.domain.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * 부동산 거래 발생 지역 데이터 query parameters
 */
@Data
@Builder
public class RegionQuery {
    private Integer minCash;
    private Integer maxCash;
    private LocalDate startDate;
    private LocalDate endDate;
}
