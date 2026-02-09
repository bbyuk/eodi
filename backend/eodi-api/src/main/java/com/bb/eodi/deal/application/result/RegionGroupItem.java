package com.bb.eodi.deal.application.result;

/**
 * 지역 그룹 Inner record (상위 법정동)
 *
 * @param code        법정동 코드
 * @param name        법정동 명
 * @param displayName 노출명
 */
public record RegionGroupItem(
        String code,
        String name,
        String displayName
) {
}
