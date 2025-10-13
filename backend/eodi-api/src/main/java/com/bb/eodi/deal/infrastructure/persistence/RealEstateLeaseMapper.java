package com.bb.eodi.deal.infrastructure.persistence;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import org.mapstruct.Mapper;

/**
 * 부동산 임대차 실거래가 Jpa entity - domain entity mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateLeaseMapper {
    RealEstateLease toDomain(RealEstateLeaseJpaEntity entity);
}
