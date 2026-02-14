package com.bb.eodi.legaldong.presentation.dto.request;


import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;

/**
 * 지역 단위 법정동 조회 API 요청 파라미터
 */
public record RegionLegalDongFindRequest(
        @Parameter(description = "조회 대상 법정동코드", example = "1100000000", required = true)
        @NotNull(message = "조회 대상 법정동 코드는 필수값입니다.")
        String code
) {
}
