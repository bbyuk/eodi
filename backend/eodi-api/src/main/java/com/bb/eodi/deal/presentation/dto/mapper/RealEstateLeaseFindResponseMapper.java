package com.bb.eodi.deal.presentation.dto.mapper;

import com.bb.eodi.deal.application.result.RealEstateLeaseSummaryResult;
import com.bb.eodi.deal.presentation.dto.response.RealEstateLeaseFindResponse;
import org.mapstruct.Mapper;

/**
 * 부동산 임대차 실거래가 조회 API 응답 DTO Mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateLeaseFindResponseMapper {

    RealEstateLeaseFindResponse toResponse(RealEstateLeaseSummaryResult result);
}
