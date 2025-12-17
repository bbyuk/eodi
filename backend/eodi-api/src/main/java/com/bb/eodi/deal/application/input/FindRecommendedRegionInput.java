package com.bb.eodi.deal.application.input;

import java.util.List;

/**
 * 추천 지역 조회 application input
 */
public record FindRecommendedRegionInput(
        Integer cash,
        List<String> housingTypes
) {
}
