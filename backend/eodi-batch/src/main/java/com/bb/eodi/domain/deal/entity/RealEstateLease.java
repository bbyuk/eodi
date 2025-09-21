package com.bb.eodi.domain.deal.entity;

import com.bb.eodi.domain.deal.type.HousingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "real_estate_lease")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RealEstateLease {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;


    // 대상지역 법정동 ID
    @Column(name = "region_id")
    private Long regionId;

    // 법정동 명
    @Column(name = "legal_dong_name")
    private String legalDongName;

    // 계약일
    @Column(name = "contract_date")
    private LocalDate contractDate;

    // 계약시작 월
    @Column(name = "contract_start_month")
    private LocalDate contractStartMonth;

    // 계약종료 월
    @Column(name = "contract_end_month")
    private LocalDate contractEndMonth;

    // 보증금
    @Column(name = "deposit")
    private Integer deposit;

    // 월세
    @Column(name = "monthly_rent")
    private Integer monthlyRent;

    // 이전 계약 보증금
    @Column(name = "previous_deposit")
    private Integer previousDeposit;

    // 이전 계약 월세
    @Column(name = "previous_monthly_rent")
    private Integer previousMonthlyRent;

    // 건축년도
    @Column(name = "build_year")
    private Integer buildYear;

    // 전용면적
    @Column(name = "net_leasable_area")
    private BigDecimal netLeasableArea;

    // 주택유형
    @Column(name = "housing_type")
    private HousingType housingType;

    // 대상명
    @Column(name = "target_name")
    private String targetName;

    // 층
    @Column(name = "floor")
    private Integer floor;

    // 생성일시
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 수정일시
   @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

}
