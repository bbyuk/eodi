package com.bb.eodi.deal.infrastructure.persistence;

import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.domain.type.TradeMethodType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 부동산 매매 데이터 JPA 엔티티
 */
@Getter
@Entity
@Table(name = "real_estate_sell")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RealEstateSellJpaEntity {

    @Builder
    public RealEstateSellJpaEntity(Long regionId, String legalDongName, LocalDate contractDate, Long price, TradeMethodType tradeMethodType, LocalDate cancelDate, Integer buildYear, BigDecimal netLeasableArea, BigDecimal landArea, BigDecimal totalFloorArea, String buyer, String seller, HousingType housingType, LocalDate dateOfRegistration, String targetName, String buildingDong, Integer floor, Boolean isLandLease) {
        this.regionId = regionId;
        this.legalDongName = legalDongName;
        this.contractDate = contractDate;
        this.price = price;
        this.tradeMethodType = tradeMethodType;
        this.cancelDate = cancelDate;
        this.buildYear = buildYear;
        this.netLeasableArea = netLeasableArea;
        this.landArea = landArea;
        this.totalFloorArea = totalFloorArea;
        this.buyer = buyer;
        this.seller = seller;
        this.housingType = housingType;
        this.dateOfRegistration = dateOfRegistration;
        this.targetName = targetName;
        this.buildingDong = buildingDong;
        this.floor = floor;
        this.isLandLease = isLandLease;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 대상 지역 법정동 ID (법정동코드 앞 5자리에 해당하는 법정동 ID)
    @Column(name = "region_id")
    private Long regionId;

    // 법정동명
    @Column(name = "legal_dong_name")
    private String legalDongName;

    // 계약일
    @Column(name = "contract_date")
    private LocalDate contractDate;

    // 거래금액 (만원)
    @Column(name = "price")
    private Long price;

    // 거래 방법
    @Column(name = "trade_method_type")
    private TradeMethodType tradeMethodType;

    // 해제사유 발생일
    @Column(name = "cancel_date")
    private LocalDate cancelDate;

    // 건축년도
    @Column(name = "build_year")
    private Integer buildYear;

    // 전용면적
    @Column(name = "net_leasable_area", precision = 10, scale = 4)
    private BigDecimal netLeasableArea;

    // 대지면적
    @Column(name = "land_area", precision = 10, scale = 4)
    private BigDecimal landArea;

    // 연면적
    @Column(name = "total_floor_area", precision = 10, scale = 4)
    private BigDecimal totalFloorArea;

    // 매수자
    @Column(name = "buyer")
    private String buyer;

    // 매도자
    @Column(name = "seller")
    private String seller;
    
    // 주택 유형
    @Column(name = "housing_type")
    private HousingType housingType;

    // 등기일자
    @Column(name = "date_of_registration")
    private LocalDate dateOfRegistration;

    // 대상명
    @Column(name = "target_name")
    private String targetName;

    // 건물 동
    @Column(name = "building_dong")
    private String buildingDong;

    // 층
    @Column(name = "floor")
    private Integer floor;
    
    // 토지임대부 여부
    @Column(name = "is_land_lease")
    private Boolean isLandLease;

    // 생성일시
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 수정일시
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
