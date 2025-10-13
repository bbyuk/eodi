package com.bb.eodi.deal.infrastructure.persistence;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 부동산 매매 실거래가 Jpa Entity - domain Entity mapper
 */
@Mapper(componentModel = "spring")
public interface RealEstateSellMapper {
    RealEstateSell toDomain(RealEstateSellJpaEntity entity);
}
