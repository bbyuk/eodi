package com.bb.eodi.address.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 주소위치 정보 조회 query 파라미터
 */
@Data
@Builder
public class AddressPositionFindQuery {
    private String roadNameCode;
    private String legalDongCode;
    private String isUnderground;
    private Integer buildingMainNo;
    private Integer buildingSubNo;

    private String buildingName;
}
