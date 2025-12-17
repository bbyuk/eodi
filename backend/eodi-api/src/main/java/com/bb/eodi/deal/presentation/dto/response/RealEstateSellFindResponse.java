package com.bb.eodi.deal.presentation.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 부동산 매매 실거래가 조회 API 응답 DTO
 */
public record RealEstateSellFindResponse(
        // 부동산 매매 데이터 id
        Long id,
        // 지역 ID
        Long regionId,
        // 법정동 full name
        String legalDongFullName,
        // 법정동 동명
        String legalDongName,
        // 거래일
        LocalDate contractDate,
        // 가격
        Long price,
        // 건축연도
        Integer buildYear,
        // 전용면적
        BigDecimal netLeasableArea,
        // 대지면적
        BigDecimal landArea,
        // 연면적
        BigDecimal totalFloorArea,
        // 주택 유형
        String housingType,
        // 등기일자
        LocalDate dateOfRegistration,
        // 대상명
        String targetName,
        // 건물동
        String buildingDong,
        // 층
        Integer floor,
        // 네이버 부동산에서 보기 URL
        String naverUrl
) {
}
