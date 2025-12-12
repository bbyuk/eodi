package com.bb.eodi.address.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 주소 위치정보 매핑 Parameter
 */
@Data
@Builder
public class AddressPositionMappingParameter {

    // 법정동주소
    private String legalDongCode;
    // 도로명주소
    private String roadNameCode;
    // 건물본번
    private Integer buildingMainNo;
    // 건물부번
    private Integer buildingSubNo;
    // 지하여부
    private String isUnderground;
    // X좌표
    private BigDecimal xPos;
    // Y좌표
    private BigDecimal yPos;
}
