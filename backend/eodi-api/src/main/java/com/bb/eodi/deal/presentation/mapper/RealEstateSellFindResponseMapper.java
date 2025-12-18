package com.bb.eodi.deal.presentation.mapper;

import com.bb.eodi.deal.application.result.RealEstateSellSummaryResult;
import com.bb.eodi.deal.presentation.dto.response.RealEstateSellFindResponse;
import org.mapstruct.Mapper;

/**
 * 부동산 매매 실거래가 조회 API 응답 dto mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateSellFindResponseMapper {
    RealEstateSellFindResponse toResponse(RealEstateSellSummaryResult result);
}
