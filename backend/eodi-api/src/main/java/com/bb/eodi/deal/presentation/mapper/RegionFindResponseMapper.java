package com.bb.eodi.deal.presentation.mapper;

import com.bb.eodi.deal.application.result.RecommendedRegionsResult;
import com.bb.eodi.deal.application.result.RegionGroupItem;
import com.bb.eodi.deal.application.result.RegionItem;
import com.bb.eodi.deal.presentation.dto.response.RegionFindResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 지역 조회 API 응답 DTO Mapper
 */
@Mapper(componentModel = "spring")
public interface RegionFindResponseMapper {

    RegionFindResponse toResponse(RecommendedRegionsResult recommendedRegionsResult);

    RegionFindResponse.RegionGroup toGroup(RegionGroupItem group);

    RegionFindResponse.RegionItem toItem(RegionItem item);

    List<RegionFindResponse.RegionItem> toItems(
            List<RegionItem> items
    );
}
