package com.bb.eodi.deal.application.result;

import java.util.List;
import java.util.Map;

/**
 * 살펴볼 만한 지역 정보 결과
 *
 * @param regionGroups  추천 지역 그룹 목록 (상위 법정동)
 * @param regions       추천 지역 목록 (법정동)
 */
public record RecommendedRegionsResult(
        Map<String, RegionGroupItem> regionGroups,
        Map<String, List<RegionItem>> regions
) {
}
