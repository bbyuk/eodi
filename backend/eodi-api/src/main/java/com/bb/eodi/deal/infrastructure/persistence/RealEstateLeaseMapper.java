package com.bb.eodi.deal.infrastructure.persistence;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 부동산 임대차 실거래가 Jpa entity - domain entity mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateLeaseMapper {
    @Mapping(source = "region.id", target = "regionId")
    RealEstateLease toDomain(RealEstateLeaseJpaEntity entity);
}
