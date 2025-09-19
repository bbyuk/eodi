package com.bb.eodi.port.out.deal.dto;

/**
 * 연립/다세대 전월세 실거래가 데이터
 */
public record MultiHouseholdHouseLeaseDataItem(
        // 지역코드
        String sggCd,
        // 법정동 명
        String umdNm,
        // 연립다세대명
        String mhouseNm,
        // 지번
        String jibun,
        // 건축년도
        String buildYear,
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
