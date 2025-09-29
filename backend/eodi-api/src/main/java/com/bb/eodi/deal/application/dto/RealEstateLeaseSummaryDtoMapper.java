package com.bb.eodi.deal.application.dto;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import org.mapstruct.Mapper;

/**
 * 부동산 임대차 실거래가 summary dto mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateLeaseSummaryDtoMapper {
    RealEstateLeaseSummaryDto toDto(RealEstateLease domain);
}
