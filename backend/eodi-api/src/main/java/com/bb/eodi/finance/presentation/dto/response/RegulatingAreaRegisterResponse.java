package com.bb.eodi.finance.presentation.dto.response;

/**
 * 부동산 규제지역 등록 API Response
 * @param count 현재 등록된 규제 지역의 수
 */
public record RegulatingAreaRegisterResponse(
        int count
) {
}
