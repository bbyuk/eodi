package com.bb.eodi.ops.infrastructure.persistence;

import com.bb.eodi.ops.domain.entity.ReferenceVersion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReferenceVersionMapper {

    ReferenceVersion toDomain(ReferenceVersionJpaEntity entity);
}
