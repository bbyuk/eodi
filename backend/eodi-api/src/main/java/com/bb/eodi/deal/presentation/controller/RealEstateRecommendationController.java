package com.bb.eodi.deal.presentation.controller;


import com.bb.eodi.common.presentation.response.PageResponse;
import com.bb.eodi.deal.application.dto.RealEstateSellSummaryDto;
import com.bb.eodi.deal.application.dto.RecommendedRegionsDto;
import com.bb.eodi.deal.application.service.RealEstateRecommendationService;
import com.bb.eodi.deal.application.dto.request.RealEstateLeaseRecommendRequestParameter;
import com.bb.eodi.deal.application.dto.request.RegionRecommendRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "부동산 실거래 데이터 기반 추천 API")
@RequestMapping("real-estate/recommendation")
@RestController
@RequiredArgsConstructor
public class RealEstateRecommendationController {

    private final RealEstateRecommendationService realEstateRecommendationService;

    @GetMapping("region")
    @Operation(summary = "살펴볼 만한 지역 조회",
            description = "보유 현금 기준으로 살펴볼 만한 지역 조회")
    public ResponseEntity<RecommendedRegionsDto> getRecommendedRegions(
            @ParameterObject @Valid
            RegionRecommendRequest requestParameter
    ) {

        return ResponseEntity.ok(
                realEstateRecommendationService.findRecommendedRegions(requestParameter.cash(), requestParameter.housingTypes())
        );
    }

    @GetMapping("sells")
    @Operation(summary = "살펴볼 만한 매매 거래 목록 조회",
        description = "보유 현금, 선택한 지역, 선택한 주택 유형을 기준으로 살펴볼 만한 매매 거래 목록 조회")
    public ResponseEntity<PageResponse<RealEstateSellSummaryDto>> getRecommendedRealEstateSells(
            @ParameterObject @Valid
            RealEstateLeaseRecommendRequestParameter requestParameter
    ) {
        return ResponseEntity.ok(
                PageResponse.from(realEstateRecommendationService.findRecommendedSells(requestParameter))
        );
    }
}
