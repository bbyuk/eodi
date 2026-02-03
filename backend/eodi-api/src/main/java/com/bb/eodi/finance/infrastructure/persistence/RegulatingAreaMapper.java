package com.bb.eodi.finance.infrastructure.persistence;

import com.bb.eodi.finance.domain.entity.RegulatingArea;
import org.mapstruct.Mapper;

/**
 * 주택담보대출 규제지역 Jpa Entity - domain entity mapper
 */
@Mapper(componentModel = "spring")
public interface RegulatingAreaMapper {
    RegulatingArea toDomain(RegulatingAreaJpaEntity jpaEntity);
    RegulatingAreaJpaEntity toJpaEntity(RegulatingArea domain);
}
