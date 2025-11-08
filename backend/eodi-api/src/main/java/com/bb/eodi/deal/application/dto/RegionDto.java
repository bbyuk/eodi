package com.bb.eodi.deal.application.dto;

/**
 * 지역 DTO
 * @param groupCode 지역 코드
 * @param code 지역 코드
 * @param name 지역 명
 * @param displayName 노출명
 * @param count 수
 */
public record RegionDto(
        Long id,
        String groupCode,
        String code,
        String name,
        String displayName,
        int count
) {
}
