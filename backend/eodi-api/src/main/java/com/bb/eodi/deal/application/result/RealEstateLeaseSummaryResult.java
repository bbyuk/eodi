package com.bb.eodi.deal.application.result;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 부동산 임대차 실거래가 domain entity <-> application dto
 */
@Data
public class RealEstateLeaseSummaryResult {
    // id
    private Long id;
    // 대상지역 법정동 ID
    private Long regionId;
    // 법정동 명
    private String legalDongName;
    // 법정동 full name
    private String legalDongFullName;
    // 계약일
    private LocalDate contractDate;
    // 계약시작 월
    private Integer contractStartMonth;
    // 계약종료 월
    private Integer contractEndMonth;
    // 보증금
    private Integer deposit;
    // 월세
    private Integer monthlyRent;
    // 이전 계약 보증금
    private Integer previousDeposit;
    // 이전 계약 월세
    private Integer previousMonthlyRent;
    // 연면적
    private BigDecimal totalFloorArea;
    // 건축년도
    private Integer buildYear;
    // 전용면적
    private BigDecimal netLeasableArea;
    // 주택유형
    private String housingType;
    // 대상명
    private String targetName;
    // 층
    private Integer floor;
    // 갱신계약청구권 사용
    private boolean useRRRight;
    // 네이버 부동산 url
    private String naverUrl;
}
