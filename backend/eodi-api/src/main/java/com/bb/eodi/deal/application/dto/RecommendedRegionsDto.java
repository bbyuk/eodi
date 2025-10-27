package com.bb.eodi.deal.application.dto;

import java.util.List;

/**
 * 살펴볼 만한 지역 정보 DTO
 * @param sellRegionGroups 매매 추천 지역 그룹 목록 (상위 법정동)
 * @param sellRegions 매매 추천 지역 목록 (법정동)
 * @param leaseRegionGroups 임대차 추천 지역 그룹 목록 (상위 법정동)
 * @param leaseRegions 임대차 추천 지역 목록 (법정동
 */
public record RecommendedRegionsDto(
        List<RegionGroupDto> sellRegionGroups,
        List<RegionDto> sellRegions,
        List<RegionGroupDto> leaseRegionGroups,
        List<RegionDto> leaseRegions
) {
}
