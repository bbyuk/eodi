package com.bb.eodi.batch.deal.load.model;

import lombok.Builder;
import lombok.Data;

/**
 * 아파트 매매 데이터 row Model
 */
@Data
@Builder
public class ApartmentSaleRow {

    // 시군구
    private String sigungu;

    // 단지명
    private String complexName;

    // 전용면적
    private String netLeasableArea;

    // 계약년월
    private String contractYearMonth;

    // 계약일
    private String contractDay;

    // 거래금액
    private String price;

    // 동
    private String unit;

    // 층
    private String floor;

    // 매수자
    private String buyer;

    // 매도자
    private String seller;

    // 건축년도
    private String buildYear;

    // 도로명
    private String roadName;

    // 해제사유발생일
    private String cancelDate;

    // 거래유형
    private String tradeMethod;

    // 등기일자
    private String registrationDate;
}
