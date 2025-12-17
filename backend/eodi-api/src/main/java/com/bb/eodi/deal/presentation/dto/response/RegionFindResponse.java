package com.bb.eodi.deal.presentation.dto.response;


import java.util.List;
import java.util.Map;

/**
 * 지역 조회 API 응답 DTO
 */
public record RegionFindResponse(
        Map<String, RegionGroup> sellRegionGroups,
        Map<String, List<RegionItem>> sellRegions,
        Map<String, RegionGroup> leaseRegionGroups,
        Map<String, List<RegionItem>> leaseRegions
) {

    /**
     * 지역 그룹 Inner record (상위 법정동)
     *
     * @param code        법정동 코드
     * @param name        법정동 명
     * @param displayName 노출명
     * @param count       수
     */
    public record RegionGroup(
            String code,
            String name,
            String displayName,
            int count
    ) {
    }

    /**
     * 지역 Inner record
     *
     * @param groupCode   지역 코드
     * @param code        지역 코드
     * @param name        지역 명
     * @param displayName 노출명
     * @param count       수
     */
    public record RegionItem(
            Long id,
            String groupCode,
            String code,
            String name,
            String displayName,
            int count
    ) {
    }
}