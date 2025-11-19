package com.bb.eodi.batch.deal.dto;

/**
 * 아파트 분양권 전매 데이터
 */
public record ApartmentPresaleRightSellDataItem(
        // 지역코드
        String sggCd,
        // 시군구 명
        String sggNm,
        // 법정동 명
        String umdNm,
        // 단지 명
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
        // 구분
        String ownershipGbn,
        // 층
        String floor,
        // 해제여부
        String cdealType,
        // 해제사유발생일
        String cdealDay,
        // 거래유형(중개 및 직거래 여부)
        String dealingGbn,
        // 중개사소재지(시군구 단위)
        String estateAgentSggNm,
        // 거래주체정보_매도자(개인/법인/공공기관/기타)
        String slerGbn,
        // 거래주체정보_매수자(개인/법인/공공기관/기타)
        String buyerGbn


) {
}
