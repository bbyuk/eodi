package com.bb.eodi.finance.presentation.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 부동산 규제지역 등록 API Request parameter
 */
public record RegulatingAreaRegisterRequest(

        @Parameter(
                description = "등록 대상 지역명",
                example = "['서울특별시', '경기도 과천시']",
                required = true,
                array = @ArraySchema(
                        schema = @Schema(
                                type = "string",
                                example = "서울특별시"
                        )
                )
        )
        @NotEmpty(message = "입력된 등록 대상 지역이 없습니다.")
        List<String> targetRegionNames,

        @Parameter(description = "적용시작일자", example = "2026-01-01")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate effectiveStartDate,

        @Parameter(description = "적용종료일자", example = "9999-12-31")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate effectiveEndDate
) {
}
