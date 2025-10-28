package com.bb.eodi.deal.application.model;

import com.bb.eodi.deal.domain.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 법정동 정보 application model mapper
 */
@Mapper(componentModel = "spring")
public interface LegalDongInfoMapper {

    @Mapping(source = "order", target = "legalDongOrder")
    Region toEntity(LegalDongInfo model);

}
