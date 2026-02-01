package com.bb.eodi.deal.presentation.dto.request;

import com.bb.eodi.deal.domain.type.HousingType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.YearMonth;
import java.util.List;

/**
 * 부동산 매매 실거래가 데이터 요청 파라미터
 */
public record RealEstateSellRequestParameter(
        @Parameter(description = "최대 거래가 (단위: 만원)", example = "100000")
        Long maxPrice,
        @Parameter(description = "최소 거래가 (단위: 만원)", example = "40000")
        Long minPrice,
        @Parameter(description = "최대 전용면적", example = "84")
        Integer maxNetLeasableArea,
        @Parameter(description = "최소 전용면적", example = "39")
        Integer minNetLeasableArea,
        @Parameter(description = "조회 계약년월 시작월", example = "202409")
        @DateTimeFormat(pattern = "yyyyMM")
        YearMonth startYearMonth,
        @Parameter(description = "조회 계약년월 종료월", example = "202409")
        @DateTimeFormat(pattern = "yyyyMM")
        YearMonth endYearMonth,

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
