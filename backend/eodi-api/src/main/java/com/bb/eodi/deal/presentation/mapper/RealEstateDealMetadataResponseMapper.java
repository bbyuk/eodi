package com.bb.eodi.deal.presentation.mapper;

import com.bb.eodi.deal.application.result.RealEstateDealMetadataRetrieveResult;
import com.bb.eodi.deal.presentation.dto.response.RealEstateDealMetadataResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RealEstateDealMetadataResponseMapper {
    RealEstateDealMetadataResponse toResponse(RealEstateDealMetadataRetrieveResult result);
}
