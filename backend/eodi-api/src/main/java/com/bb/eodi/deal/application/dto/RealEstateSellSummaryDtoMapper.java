package com.bb.eodi.deal.application.dto;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import org.mapstruct.Mapper;

/**
 * 부동산 매매 실거래가 요약 dto mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateSellSummaryDtoMapper {
    RealEstateSellSummaryDto toDto(RealEstateSell domainEntity);
}
