package com.bb.eodi.deal.domain.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * 부동산 거래 발생 지역 데이터 query parameters
 */
@Data
@Builder
public class RegionQuery {
    private Integer minPrice;
    private Integer maxPrice;
    private LocalDate startDate;
    private LocalDate endDate;
}
