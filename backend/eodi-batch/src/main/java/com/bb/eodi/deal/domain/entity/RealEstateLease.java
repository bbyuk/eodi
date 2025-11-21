package com.bb.eodi.deal.domain.entity;

import com.bb.eodi.deal.domain.type.HousingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    public RealEstateLease(Long regionId, Integer landLotMainNo, Integer landLotSubNo, Boolean isMountain, String legalDongName, LocalDate contractDate, Integer contractStartMonth, Integer contractEndMonth, Integer deposit, Integer monthlyRent, Integer previousDeposit, Integer previousMonthlyRent, BigDecimal totalFloorArea, Integer buildYear, BigDecimal netLeasableArea, HousingType housingType, String targetName, Integer floor, boolean useRRRight) {
        this.regionId = regionId;
        this.landLotMainNo = landLotMainNo;
        this.landLotSubNo = landLotSubNo;
        this.isMountain = isMountain;
        this.legalDongName = legalDongName;
        this.contractDate = contractDate;
        this.contractStartMonth = contractStartMonth;
        this.contractEndMonth = contractEndMonth;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.previousDeposit = previousDeposit;
        this.previousMonthlyRent = previousMonthlyRent;
        this.totalFloorArea = totalFloorArea;
        this.buildYear = buildYear;
        this.netLeasableArea = netLeasableArea;
        this.housingType = housingType;
        this.targetName = targetName;
        this.floor = floor;
        this.useRRRight = useRRRight;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 대상지역 법정동 ID
    @Column(name = "region_id")
    private Long regionId;

    // 지번본번
    @Column(name = "land_lot_main_no")
    private Integer landLotMainNo;

    // 산 여부
    @Column(name = "is_mountain")
    private Boolean isMountain;

    // 지번부번
    @Column(name = "land_lot_sub_no")
    private Integer landLotSubNo;

    // 법정동 명
    @Column(name = "legal_dong_name")
    private String legalDongName;

    // 계약일
    @Column(name = "contract_date")
    private LocalDate contractDate;

    // 계약시작 월
    @Column(name = "contract_start_month")
    private Integer contractStartMonth;

    // 계약종료 월
    @Column(name = "contract_end_month")
    private Integer contractEndMonth;

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

    // 연면적
    @Column(name = "total_floor_area")
    private BigDecimal totalFloorArea;

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

    // 갱신계약청구권 사용
    @Column(name = "use_rr_right")
    private boolean useRRRight;

    // 생성일시
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 수정일시
   @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

}
