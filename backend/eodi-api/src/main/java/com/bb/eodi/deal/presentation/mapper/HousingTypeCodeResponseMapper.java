package com.bb.eodi.deal.presentation.mapper;

import com.bb.eodi.deal.application.result.HousingTypeCodeRetrieveResult;
import com.bb.eodi.deal.presentation.dto.response.HousingTypeCodeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HousingTypeCodeResponseMapper {

    default HousingTypeCodeResponse toResponse(List<HousingTypeCodeRetrieveResult> result) {
        return new HousingTypeCodeResponse(toItems(result));
    }

    List<HousingTypeCodeResponse.Item> toItems(List<HousingTypeCodeRetrieveResult> result);
    HousingTypeCodeResponse.Item toItem(HousingTypeCodeRetrieveResult result);
}
