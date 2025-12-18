package com.bb.eodi.deal.infrastructure.persistence;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 부동산 임대차 실거래가 Jpa entity - domain entity mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateLeaseMapper {
    @Mappings({
            @Mapping(target = "xPos", source = "XPos"),
            @Mapping(target = "yPos", source = "YPos")
    })
    RealEstateLease toDomain(RealEstateLeaseJpaEntity entity);
}
