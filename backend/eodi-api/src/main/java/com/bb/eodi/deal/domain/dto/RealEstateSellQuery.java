package com.bb.eodi.deal.domain.dto;

import com.bb.eodi.deal.domain.type.HousingType;
import lombok.Builder;
import lombok.Data;

/**
 * 부동산 매매 실거래가 데이터 조회 query parameters
 */
@Data
@Builder
public class RealEstateSellQuery {
    // 가격 (만원단위)
    private int maxPrice;
    private int minPrice;
    // 층
    private Integer floor;
    // 주택유형
    private HousingType housingType;
}
