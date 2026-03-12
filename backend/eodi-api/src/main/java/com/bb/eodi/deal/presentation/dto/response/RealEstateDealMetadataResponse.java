package com.bb.eodi.deal.presentation.dto.response;

import java.time.LocalDate;

/**
 * 부동산 실거래가 추천 데이터 메타데이터 조회 응답
 * @param updateDate
 */
public record RealEstateDealMetadataResponse(
        LocalDate updateDate
) {
}
