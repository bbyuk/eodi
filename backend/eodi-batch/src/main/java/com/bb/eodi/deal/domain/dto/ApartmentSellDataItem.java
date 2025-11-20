package com.bb.eodi.deal.domain.dto;

/**
 * 아파트 매매 데이터 Item
 */
public record ApartmentSellDataItem(
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
        // 거래금액(만원)
        String dealAmount,
        // 층
        String floor,
        // 건축년도,
        String buildYear,
        // 해제여부,
        String cdealType,
        // 해제사유발생일
        String cdealDay,
        // 거래유형(중개 및 직거래 여부)
        String dealingGbn,
        // 중개사소재지(시군구 단위)
        String estateAgentSggNm,
        // 등기일자(rgstDate)
        String rgstDate,
        // 아파트 동명
        String aptDong,
        // 거래주체정보_매도자(개인/법인/공공기관/기타)
        String slerGbn,
        // 거래주체정보_매수자(개인/법인/공공기관/기타)
        String buyerGbn,
        // 토지임대부 아파트 여부
        String landLeaseholdGbn
) {
}
