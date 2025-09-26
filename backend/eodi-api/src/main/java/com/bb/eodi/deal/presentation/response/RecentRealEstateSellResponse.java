package com.bb.eodi.deal.presentation.response;

import com.bb.eodi.deal.application.dto.RealEstateSellSummaryDto;

import java.util.List;

/**
 * 부동산 매매 최근 실거래가 데이터 응답 DTO
 */
public record RecentRealEstateSellResponse(
        List<RealEstateSellSummaryDto> data,
        int totalCount,
        int pageNum,
        int pageSize
) {
}
