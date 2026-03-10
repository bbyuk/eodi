package com.bb.eodi.deal.presentation.dto.response;

import java.util.List;

/**
 * 주택 유형 코드 조회 API 응답
 */
public record HousingTypeCodeResponse(
    List<Item> items
) {

    public record Item(
            String code,
            String name
    ) {}
}
