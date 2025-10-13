package com.bb.eodi.deal.application.dto;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 부동산 임대차 실거래가 summary dto mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateLeaseSummaryDtoMapper {

    @Mappings({
            @Mapping(target = "housingType", expression = "java(domain.getHousingType().code())")
    })
    RealEstateLeaseSummaryDto toDto(RealEstateLease domain);
}
