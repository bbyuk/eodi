package com.bb.eodi.domain.deal.entity;

import com.bb.eodi.domain.deal.type.HousingType;
import com.bb.eodi.domain.deal.type.TradeMethodType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import javax.swing.plaf.synth.Region;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 부동산 매매 데이터 엔티티
 */
@Getter
public class RealEstateDeal {

    @Builder
    public RealEstateDeal(Region region, String roadName, LocalDate contractDate, int price, TradeMethodType tradeMethodType, LocalDate cancelDate, int buildYear, BigDecimal netLeasableArea, BigDecimal landArea, String buyer, String seller, HousingType housingType, LocalDate registrationDate, String targetName, String dong, Integer floor, BigDecimal totalFloorArea, BigDecimal siteArea) {
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
        this.targetName = targetName;
        this.dong = dong;
        this.floor = floor;
        this.totalFloorArea = totalFloorArea;
    }

    @Id
    private Long id;

    // 행정구역
    private Region region;

    // 도로명
    private String roadName;

    // 계약일
    private LocalDate contractDate;

    // 거래금액 (만원)
    private int price;

    // 거래 방법
    private TradeMethodType tradeMethodType;

    // 해제사유 발생일
    private LocalDate cancelDate;

    // 건축년도
    private int buildYear;

    // 전용면적
    private BigDecimal netLeasableArea;

    // 대지면적
    private BigDecimal landArea;

    // 연면적
    private BigDecimal totalFloorArea;

    // 매수자
    private String buyer;

    // 매도자
    private String seller;
    
    // 주택 유형
    private HousingType housingType;

    // 등기일자
    private LocalDate registrationDate;

    // 대상명
    private String targetName;

    // 건물 동
    private String dong;

    // 층
    private Integer floor;

    // 생성일시
    private LocalDateTime createdAt;

    // 수정일시
    private LocalDateTime updatedAt;

}
