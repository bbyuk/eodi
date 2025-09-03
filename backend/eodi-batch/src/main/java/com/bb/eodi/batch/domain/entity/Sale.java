package com.bb.eodi.batch.domain.entity;

import com.bb.eodi.batch.domain.type.HousingType;
import com.bb.eodi.batch.domain.type.TradeMethodType;
import com.bb.eodi.batch.domain.vo.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 부동산 매매 데이터 엔티티
 */
@Getter
@Table(name = "real_esate_sale")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sale {

    @Builder
    public Sale(Region region, String roadName, LocalDate contractDate, int price, TradeMethodType tradeMethodType, LocalDate cancelDate, int buildYear, BigDecimal netLeasableArea, BigDecimal landArea, String buyer, String seller, HousingType housingType, LocalDate registrationDate, String buildingName, Integer dong, Integer floor, BigDecimal totalFloorArea, BigDecimal siteArea) {
        this.region = region;
        this.roadName = roadName;
        this.contractDate = contractDate;
        this.price = price;
        this.tradeMethodType = tradeMethodType;
        this.cancelDate = cancelDate;
        this.buildYear = buildYear;
        this.netLeasableArea = netLeasableArea;
        this.landArea = landArea;
        this.buyer = buyer;
        this.seller = seller;
        this.housingType = housingType;
        this.registrationDate = registrationDate;
        this.buildingName = buildingName;
        this.dong = dong;
        this.floor = floor;
        this.totalFloorArea = totalFloorArea;
        this.siteArea = siteArea;
    }

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    // 행정구역
    @Embedded
    private Region region;

    // 도로명
    @Column(name = "road_name")
    private String roadName;

    // 계약일
    @Column(name = "contract_date")
    private LocalDate contractDate;

    // 거래금액 (만원)
    @Column(name = "price")
    private int price;

    // 거래 방법
    @Column(name = "trade_method_type")
    private TradeMethodType tradeMethodType;

    // 해제사유 발생일
    @Column(name = "cancel_date")
    private LocalDate cancelDate;

    // 건축년도
    @Column(name = "build_year")
    private int buildYear;

    // 전용면적
    @Column(name = "net_leasable_area", precision = 10, scale = 4)
    private BigDecimal netLeasableArea;

    // 대지면적
    @Column(name = "land_area", precision = 10, scale = 4)
    private BigDecimal landArea;

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
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    // 건물 || 단지명
    @Column(name = "building_name")
    private String buildingName;

    // 건물 동
    @Column(name = "dong")
    private Integer dong;

    // 층
    @Column(name = "floor")
    private Integer floor;

    // 연면적
    @Column(name = "total_floor_area", precision = 10, scale = 4)
    private BigDecimal totalFloorArea;

    // 대지면적
    @Column(name = "site_area", precision = 10, scale = 4)
    private BigDecimal siteArea;
}
