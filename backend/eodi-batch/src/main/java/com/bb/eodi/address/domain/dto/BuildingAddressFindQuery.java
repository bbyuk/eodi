package com.bb.eodi.address.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 건물주소 조회 쿼리 파라미터
 */
@Data
@Builder
public class BuildingAddressFindQuery {
    private String legalDongCode;
    private Integer landLotMainNo;
    private Integer landLotSubNo;
    private String isMountain;
}
