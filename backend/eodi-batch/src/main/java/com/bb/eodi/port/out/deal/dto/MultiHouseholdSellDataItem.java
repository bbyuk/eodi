package com.bb.eodi.port.out.deal.dto;

/**
 * 연립/다세대주택 매매 데이터
 */
public record MultiHouseholdSellDataItem(
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
        // 대지권면적
        String landAr,
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
        // 해제여부
        String cdealType,
        // 해제사유발생일
        String cdealDay,
        // 거래유형(중개 및 직거래 여부)
        String delingGbn,
        // 중개사소재지(시군구 단위)
        String estateAgentSggNm,
        // 등기일자
        String rgstDate,
        // 거래주체정보_매도자(개인/법인/공공기관/기타)
        String slerGbn,
        // 거래주체정보_매수자(개인/법인/공공기관/기타)
        String buyerGbn
) {
}
