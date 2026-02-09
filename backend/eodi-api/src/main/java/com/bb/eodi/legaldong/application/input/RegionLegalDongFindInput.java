package com.bb.eodi.legaldong.application.input;

/**
 * 법정동 지역 단위 (시군구) 조회 input dto
 */
public record RegionLegalDongFindInput(
        // 조회 대상 최상위 법정동(시도) 법정동 ID
        String code
) {
}
