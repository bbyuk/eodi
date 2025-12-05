package com.bb.eodi.address.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 지번주소 조회 쿼리 파라미터
 */
@Data
@Builder
public class LandLotAddressFindQuery {
    private String legalDongCode;
    private Integer landLotMainNo;
    private Integer landLotSubNo;
}
