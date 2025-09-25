package com.bb.eodi.deal.domain.entity;

import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.domain.type.TradeMethodType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 부동산 매매 실거래가 데이터 domain entity
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RealEstateSell {
    private Long id;
    // 대상 지역 법정동 ID (법정동코드 앞 5자리에 해당하는 법정동 ID)
    private Long regionId;
    // 법정동명
    private String legalDongName;
    // 계약일
    private LocalDate contractDate;
    // 거래금액 (만원)
    private Long price;
    // 거래 방법
    private TradeMethodType tradeMethodType;
    // 해제사유 발생일
    private LocalDate cancelDate;
    // 건축년도
    private Integer buildYear;
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
    private LocalDate dateOfRegistration;
    // 대상명
    private String targetName;
    // 건물 동
    private String buildingDong;
    // 층
    private Integer floor;
    // 토지임대부 여부
    private Boolean isLandLease;
    // 생성일시
    private LocalDateTime createdAt;
    // 수정일시
    private LocalDateTime updatedAt;
}
