package com.bb.eodi.batch.deal.dto;

/**
 * 월별 적재 대상 법정동 Dto
 * legal_dong 테이블에서 조회
 */
public record MonthlyLoadTargetLegalDongDto(
        String regionCode
) {
}
