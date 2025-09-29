package com.bb.eodi.deal.domain.entity;

import com.bb.eodi.deal.domain.type.HousingType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 부동산 임대차 실거래가 데이터 domain entity
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RealEstateLease {
    private Long id;
    // 대상지역 법정동 ID
    private Long regionId;
    // 법정동 명
    private String legalDongName;
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
    private HousingType housingType;
    // 대상명
    private String targetName;
    // 층
    private Integer floor;
    // 갱신계약청구권 사용
    private boolean useRRRight;
    // 생성일시
    private LocalDateTime createdAt;
    // 수정일시
    private LocalDateTime updatedAt;
}
