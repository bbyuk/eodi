package com.bb.eodi.deal.job.dto;

/**
 * 단독/다가구주택 매매 데이터
 */
public record MultiUnitDetachedSellDataItem(
        // 지역코드
        String sggCd,
        // 법정동 명
        String umdNm,
        // 주택유형(단독/다가구)
        String houseType,
        // 지번
        String jibun,
        // 연면적
        String totalFloorAr,
        // 대지면적
        String plottageAr,
        // 계약년도
        String dealYear,
        // 계약월
        String dealMonth,
        // 계약일
        String dealDay,
        // 거래금액(만원)
        String dealAmount,
        // 건축년도
        String buildYear,
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
        //거래주체정보_매수자(개인/법인/공공기관/기타)
        String buyerGbn
) {
}
