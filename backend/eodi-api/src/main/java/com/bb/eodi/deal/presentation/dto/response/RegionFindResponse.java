package com.bb.eodi.deal.presentation.dto.response;


import com.bb.eodi.deal.application.result.RegionGroupItem;
import com.bb.eodi.deal.application.result.RegionItem;

import java.util.List;
import java.util.Map;

/**
 * 지역 조회 API 응답 DTO
 */
public record RegionFindResponse(
        Map<String, RegionGroupItem> regionGroups,
        Map<String, List<RegionItem>> regions
) {
}