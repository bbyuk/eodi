package com.bb.eodi.address.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 주소위치 좌표 정보 dto
 */
@Data
@AllArgsConstructor
public class AddressPosition {
    private BigDecimal xPos;
    private BigDecimal yPos;
    private String buildingName;
}
