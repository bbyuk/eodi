package com.bb.eodi.port.out.deal.dto;

/**
 * 단독/다가구주택 전/월세 데이터
 */
public record MultiUnitDetachedLeaseDataItem(
        // 지역코드
        String sggCd,
        // 법정동 명
        String umdNm,
        // 연면적
        String totalFloorAr,
        // 계약년도
        String dealYear,
        // 계약월
        String dealMonth,
        // 계약일
        String dealDay,
        // 보증금액(만원)
        String deposit,
        // 주택 타입
        String houseType,
        // 월세금액(만원)
        String monthlyRent,
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
        String preMonthlyRent,
        String tempSggNm
) {
}
