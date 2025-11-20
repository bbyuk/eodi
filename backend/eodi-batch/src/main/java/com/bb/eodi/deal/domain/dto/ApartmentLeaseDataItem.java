package com.bb.eodi.deal.domain.dto;

/**
 * 아파트 전/월세 데이터
 */
public record ApartmentLeaseDataItem(
        // 법정동코드 상위 5자리
        String sggCd,
        // 법정동 명
        String umdNm,
        // 단지명
        String aptNm,
        // 지번
        String jibun,
        // 전용면적
        String excluUseAr,
        // 계약년도
        String dealYear,
        // 계약월
        String dealMonth,
        // 계약일
        String dealDay,
        // 보증금액(만원)
        String deposit,
        // 월세금액(만원)
        String monthlyRent,
        // 층
        String floor,
        // 건축년도
        String buildYear,
        // 계약기간
        String contractTerm,
        // 계약구분
        String contractType,
        // 갱신요구권사용
        String useRRRight,
        // 종전계약보증금
        String preDeposit,
        // 종전계약월세
        String preMonthlyRent
) {
}
