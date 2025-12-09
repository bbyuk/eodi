package com.bb.eodi.address.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * 도로명주소 조회 쿼리 파라미터
 */
@Getter
@Builder
public class RoadNameAddressQueryParameter {
    // 법정동코드
    private String legalDongCode;
    // 지번본번
    private Integer landLotMainNo;
    // 지번부번
    private Integer landLotSubNo;
}
