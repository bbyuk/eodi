package com.bb.eodi.deal.application.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 부동산 실거래가 메타데이터 조회 결과
 */
@Data
@AllArgsConstructor
public class RealEstateDealMetadataRetrieveResult {
    private LocalDate updateDate;
}
