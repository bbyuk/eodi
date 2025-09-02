package com.bb.eodi.batch.domain.entity;

import com.bb.eodi.batch.domain.type.TradeMethod;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Sale {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    // 시도
    @Column(name = "si_do")
    private String sido;

    // 시군구
    @Column(name = "si_gun_gu")
    private String sigungu;

    // 동
    @Column(name = "dong")
    private String dong;

    // 계약일
    @Column(name = "contract_date")
    private LocalDate contractDate;

    // 거래금액 (만원)
    @Column(name = "price")
    private int price;

    // 거래 방법
    @Column(name = "trade_method")
    @Enumerated(EnumType.STRING)
    private TradeMethod tradeMethod;

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

}
