package com.bb.eodi.deal.presentation.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

/**
 * 지역 추천 요청 파라미터
 */
public record RegionRecommendRequest(

        @NotNull(message = "보유 현금은 필수값입니다.")
        @Positive(message = "보유 현금은 0보다 커야합니다.")
        @Parameter(description = "보유 현금 (단위: 만 원)", example = "24000")
        Integer cash,

        @Parameter(description = "대상 주택 유형 목록", example = "AP")
        List<String> housingTypes
) {
}
