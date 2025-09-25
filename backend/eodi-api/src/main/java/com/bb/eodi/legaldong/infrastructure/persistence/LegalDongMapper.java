package com.bb.eodi.legaldong.infrastructure.persistence;

import com.bb.eodi.legaldong.domain.entity.LegalDong;
import org.mapstruct.Mapper;

/**
 * 법정동 mapstruct mapper
 */
@Mapper(componentModel = "spring")
public interface LegalDongMapper {
    LegalDong toDomain(LegalDongJpaEntity entity);
    LegalDongJpaEntity toJpaEntity(LegalDong domain);
}
