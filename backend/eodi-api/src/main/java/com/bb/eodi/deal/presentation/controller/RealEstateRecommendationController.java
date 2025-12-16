package com.bb.eodi.deal.presentation.controller;


import com.bb.eodi.common.presentation.response.PageResponse;
import com.bb.eodi.deal.application.result.RealEstateSellSummaryResult;
import com.bb.eodi.deal.application.result.RecommendedRegionsResult;
import com.bb.eodi.deal.presentation.dto.request.RealEstateSellRecommendRequestParameter;
import com.bb.eodi.deal.presentation.dto.request.RegionRecommendRequest;
import com.bb.eodi.deal.application.service.RealEstateRecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "부동산 실거래 데이터 기반 추천 API")
@RequestMapping("real-estate/recommendation")
@RestController
@RequiredArgsConstructor
public class RealEstateRecommendationController {

    private final RealEstateRecommendationService realEstateRecommendationService;

    @GetMapping("region")
    @Operation(summary = "살펴볼 만한 지역 조회",
            description = "보유 현금 기준으로 살펴볼 만한 지역 조회")
    public ResponseEntity<RecommendedRegionsResult> getRecommendedRegions(
            @ParameterObject @Valid
            RegionRecommendRequest requestParameter
    ) {

        return ResponseEntity.ok(
                realEstateRecommendationService.findRecommendedRegions(requestParameter)
        );
    }

    @GetMapping("sells")
    @Operation(summary = "살펴볼 만한 매매 거래 목록 조회",
            description = "보유 현금, 선택한 지역, 선택한 주택 유형을 기준으로 살펴볼 만한 매매 거래 목록 조회")
    public ResponseEntity<PageResponse<RealEstateSellSummaryResult>> getRecommendedRealEstateSells(
            @ParameterObject @Valid @ModelAttribute
            RealEstateSellRecommendRequestParameter requestParameter,
            @ParameterObject
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                PageResponse.from(realEstateRecommendationService.findRecommendedSells(requestParameter, pageable))
        );
    }

//    @GetMapping("leases")
//    @Operation(summary = "살펴볼 만한 임대차 거래 목록 조회",
//            description = "보유 현금, 선택한 지역, 선택한 주택 유형을 기준으로 살펴볼 만한 임대차 거래 목록 조회")
//    public ResponseEntity<PageResponse<RealEstateLeaseSummaryDto>> getRecommendedRealEstateLeases(
//            @ParameterObject @Valid
//            RealEstateLeaseRecommendRequestParameter requestParameter,
//            @ParameterObject
//            Pageable pageable
//    ) {
//        return ResponseEntity.ok(
//                PageResponse.from(realEstateRecommendationService.findRecommendedLeases(requestParameter, pageable))
//        );
//    }
}
