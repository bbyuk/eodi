package com.bb.eodi.deal.application.result;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 부동산 매매 데이터
 */
@Data
public class RealEstateSellSummaryResult {
    // 부동산 매매 데이터 id
    private Long id;
    // 지역 ID
    private Long regionId;
    // 법정동 full name
    private String legalDongFullName;
    // 법정동 동명
    private String legalDongName;
    // 거래일
    private LocalDate contractDate;
    // 가격
    private Long price;
    // 건축연도
    private Integer buildYear;
    // 전용면적
    private BigDecimal netLeasableArea;
    // 대지면적
    private BigDecimal landArea;
    // 연면적
    private BigDecimal totalFloorArea;
    // 주택 유형
    private String housingType;
    // 등기일자
    private LocalDate dateOfRegistration;
    // 대상명
    private String targetName;
    // 건물동
    private String buildingDong;
    // 층
    private Integer floor;
    // 네이버 부동산에서 보기 URL
    private String naverUrl;
}
