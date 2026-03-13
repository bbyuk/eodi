package com.bb.eodi.deal.presentation.controller;


import com.bb.eodi.common.model.CursorRequest;
import com.bb.eodi.common.presentation.response.CursorResponse;
import com.bb.eodi.deal.application.service.RealEstateDealMetadataService;
import com.bb.eodi.deal.application.service.RealEstateRecommendationService;
import com.bb.eodi.deal.presentation.adapter.FindRealEstateLeaseInputAdapter;
import com.bb.eodi.deal.presentation.adapter.FindRealEstateSellInputAdapter;
import com.bb.eodi.deal.presentation.adapter.FindRegionInputAdapter;
import com.bb.eodi.deal.presentation.dto.request.RealEstateLeaseRecommendRequestParameter;
import com.bb.eodi.deal.presentation.dto.request.RealEstateSellRecommendRequestParameter;
import com.bb.eodi.deal.presentation.dto.request.RegionRecommendRequest;
import com.bb.eodi.deal.presentation.dto.response.RealEstateDealMetadataResponse;
import com.bb.eodi.deal.presentation.dto.response.RealEstateLeaseFindResponse;
import com.bb.eodi.deal.presentation.dto.response.RealEstateSellFindResponse;
import com.bb.eodi.deal.presentation.dto.response.RegionFindResponse;
import com.bb.eodi.deal.presentation.mapper.RealEstateDealMetadataResponseMapper;
import com.bb.eodi.deal.presentation.mapper.RealEstateLeaseFindResponseMapper;
import com.bb.eodi.deal.presentation.mapper.RealEstateSellFindResponseMapper;
import com.bb.eodi.deal.presentation.mapper.RegionFindResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@Tag(name = "부동산 실거래 데이터 기반 추천 API")
@RequestMapping("real-estate/recommendation")
@RestController
@RequiredArgsConstructor
public class RealEstateRecommendationController {

    // mapper
    private final RealEstateSellFindResponseMapper realEstateSellFindResponseMapper;
    private final RealEstateLeaseFindResponseMapper realEstateLeaseFindResponseMapper;
    private final RegionFindResponseMapper regionFindResponseMapper;
    private final RealEstateDealMetadataResponseMapper realEstateDealMetadataResponseMapper;

    private final FindRegionInputAdapter findRegionInputAdapter;
    private final FindRealEstateSellInputAdapter findRealEstateSellInputAdapter;
    private final FindRealEstateLeaseInputAdapter findRealEstateLeaseInputAdapter;

    private final RealEstateRecommendationService realEstateRecommendationService;
    private final RealEstateDealMetadataService realEstateDealMetadataService;

    private final ThreadPoolTaskExecutor streamingExecutor;

    @GetMapping("sell/region")
    @Operation(summary = "살펴볼 만한 매매 지역 조회",
            description = "보유 현금 기준으로 살펴볼 만한 매매 지역 조회")
    public ResponseEntity<RegionFindResponse> getRecommendedSellRegions(
            @ParameterObject @Valid
            RegionRecommendRequest requestParameter
    ) {

        return ResponseEntity.ok(
                regionFindResponseMapper.toResponse(
                        realEstateRecommendationService.findRecommendedSellRegions(
                                findRegionInputAdapter.toInput(requestParameter)
                        )
                )
        );
    }

    @GetMapping("lease/region")
    @Operation(summary = "살펴볼 만한 임대차 지역 조회",
            description = "보유 현금 기준으로 살펴볼 만한 임대차 지역 조회"
    )
    public ResponseEntity<RegionFindResponse> getRecommendedLeaseRegions(
            @ParameterObject @Valid
            RegionRecommendRequest requestParameter
    ) {
        return ResponseEntity.ok(
                regionFindResponseMapper.toResponse(
                        realEstateRecommendationService.findRecommendedLeaseRegions(
                                findRegionInputAdapter.toInput(requestParameter)
                        )
                )
        );
    }

    @GetMapping(value = "v2/region", produces = MediaType.APPLICATION_NDJSON_VALUE)
    @Operation(summary = "살펴볼 만한 지역 조회 V2", description = "보유 현금 기준으로 살펴볼 만한 지역 조회")
    public ResponseBodyEmitter getRecommendedRegionsV2(
            @ParameterObject @Valid
            RegionRecommendRequest requestParameter
    ) {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter(60_000L);

        streamingExecutor.execute(() -> {
            try {
                realEstateRecommendationService.findRecommendedRegionsV2(
                        emitter,
                        findRegionInputAdapter.toInput(requestParameter)
                );

                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }

        });


        return emitter;
    }


    @GetMapping("sell")
    @Operation(summary = "살펴볼 만한 매매 거래 목록 조회",
            description = "보유 현금, 선택한 지역, 선택한 주택 유형을 기준으로 살펴볼 만한 매매 거래 목록 조회")
    public ResponseEntity<CursorResponse<RealEstateSellFindResponse>> getRecommendedRealEstateSells(
            @ParameterObject @Valid @ModelAttribute
            RealEstateSellRecommendRequestParameter requestParameter,
            @ParameterObject
            CursorRequest cursorRequest
    ) {
        return ResponseEntity.ok(
                CursorResponse.from(
                        realEstateRecommendationService
                                .findRecommendedSells(
                                        findRealEstateSellInputAdapter.toInput(requestParameter),
                                        cursorRequest
                                )
                                .map(realEstateSellFindResponseMapper::toResponse)
                )
        );
    }

    @GetMapping("lease")
    @Operation(summary = "살펴볼 만한 임대차 거래 목록 조회",
            description = "보유 현금, 선택한 지역, 선택한 주택 유형을 기준으로 살펴볼 만한 임대차 거래 목록 조회")
    public ResponseEntity<CursorResponse<RealEstateLeaseFindResponse>> getRecommendedRealEstateLeases(
            @ParameterObject @Valid
            RealEstateLeaseRecommendRequestParameter requestParameter,
            @ParameterObject
            CursorRequest cursorRequest
    ) {
        return ResponseEntity.ok(
                CursorResponse.from(
                        realEstateRecommendationService
                                .findRecommendedLeases(
                                        findRealEstateLeaseInputAdapter.toInput(requestParameter),
                                        cursorRequest)
                                .map(realEstateLeaseFindResponseMapper::toResponse)
                )
        );
    }

    @GetMapping("metadata")
    @Operation(summary = "살펴볼 만한 부동산 실거래가 거래 메타데이터 조회",
            description = "최근 업데이트 일자 등 부동산 실거래가 데이터의 메타데이터를 조회한다.")
    public ResponseEntity<RealEstateDealMetadataResponse> getDealRecommendationMetadata() {
        return ResponseEntity.ok(
                realEstateDealMetadataResponseMapper.toResponse(
                        realEstateDealMetadataService.findRealEstateDealMetadata()
                )
        );
    }

}
