package com.bb.eodi.deal.presentation.request;

import com.bb.eodi.deal.domain.type.HousingType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 부동산 임대차 실거래가 데이터 요청 파라미터
 */
public record RealEstateLeaseRequestParameter(

        @Parameter(description = "최대 보증금 (단위: 만원)", example = "50000")
        Integer maxDeposit,
        @Parameter(description = "최소 보증금 (단위: 만원)", example = "1000")
        Integer minDeposit,
        @Parameter(description = "최대 월세 (단위: 만원)", example = "130")
        Integer maxMonthlyRentFee,
        @Parameter(description = "최소 월세 (단위: 만원", example = "30")
        Integer minMonthlyRentFee,
        @Parameter(description = "최대 전용면적", example = "84")
        Integer maxNetLeasableArea,
        @Parameter(description = "최소 전용면적", example = "18")
        Integer minNetLeasableArea,
        @Parameter(
                description = "조회 대상 지역 ID 목록",
                example = "[14285,1612]",
                array = @ArraySchema(
                        schema = @Schema(
                                type = "integer",
                                format = "int64",
                                example = "14285"
                        )
                )
        )
        List<Long> targetRegionIds,
        @Parameter(
                description = "조회 대상 주택 유형 목록",
                example = "[]",
                array = @ArraySchema(
                        schema = @Schema(
                                type = "string",
                                allowableValues = {"AP", "MH", "DT", "MU", "OF", "PR", "OR", "O"}
                        )
                )
        )
        List<HousingType> targetHousingTypes
) {
}
