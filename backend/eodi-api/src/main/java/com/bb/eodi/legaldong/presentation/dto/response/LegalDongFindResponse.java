package com.bb.eodi.legaldong.presentation.dto.response;

import java.util.List;

/**
 * 법정동 조회 API 응답 DTO
 */
public record LegalDongFindResponse(
    List<Item> items
) {
    public record Item(
            // 법정동코드
            String code,
            // 법정동명
            String name,
            // 법정동 표기명
            String displayName
    ) {};
}
