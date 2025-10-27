package com.bb.eodi.deal.application.dto;

/**
 * 지역 DTO
 * @param groupCode 지역 그룹 코드
 * @param code 지역 코드
 * @param name 지역 명
 * @param displayName 노출명
 */
public record RegionDto(
        String groupCode,
        String code,
        String name,
        String displayName
) {
}
