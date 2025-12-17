package com.bb.eodi.deal.application.result.mapper;

import com.bb.eodi.deal.application.result.RealEstateLeaseSummaryResult;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 부동산 임대차 실거래가 summary dto mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateLeaseSummaryResultMapper {

    @Mappings({
            @Mapping(target = "housingType", expression = "java(domain.getHousingType().code())")
    })
    RealEstateLeaseSummaryResult toResult(RealEstateLease domain);
}
