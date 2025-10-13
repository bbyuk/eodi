package com.bb.eodi.deal.application.dto;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 부동산 매매 실거래가 요약 dto mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateSellSummaryDtoMapper {
    @Mappings({
            @Mapping(target = "housingType", expression = "java(domainEntity.getHousingType().code())")
    })
    RealEstateSellSummaryDto toDto(RealEstateSell domainEntity);
}
