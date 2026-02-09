package com.bb.eodi.deal.application.result;

/**
 * 지역 Inner record
 *
 * @param groupCode   지역 코드
 * @param code        지역 코드
 * @param name        지역 명
 * @param displayName 노출명
 */
public record RegionItem(
        Long id,
        String groupCode,
        String code,
        String name,
        String displayName
) {
}