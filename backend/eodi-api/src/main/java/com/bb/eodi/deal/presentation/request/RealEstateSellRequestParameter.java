package com.bb.eodi.deal.presentation.request;

import com.bb.eodi.deal.domain.type.HousingType;

/**
 * 부동산 매매 실거래가 데이터 요청 파라미터
 */
public record RealEstateSellRequestParameter(
        //
        int maxPrice,
        int minPrice,
        Integer floor,
        HousingType housingType
) {
}
