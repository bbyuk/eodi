package com.bb.eodi.deal.presentation.controller;

import com.bb.eodi.common.presentation.response.PageResponse;
import com.bb.eodi.deal.application.dto.RealEstateLeaseSummaryDto;
import com.bb.eodi.deal.application.service.RealEstateLeaseService;
import com.bb.eodi.deal.domain.dto.RealEstateLeaseQuery;
import com.bb.eodi.deal.presentation.request.RealEstateLeaseRequestParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 부동산 임대차 실거래가 데이터 API 컨트롤러
 */
@Tag(name = "부동산 임대차 실거래가", description = "부동산 임대차 실거래가 데이터 API")
@RequestMapping("real-estate/lease")
@RestController
@RequiredArgsConstructor
public class RealEstateLeaseController {

    private final RealEstateLeaseService realEstateLeaseService;

    /**
     * 부동산 임대차 거래 목록 페이징 조회
     * @param requestParameter 요청 파라미터 dto
     * @param pageable 페이징 dto
     * @return 부동산 임대차 거래 목록 Page
     */
    @GetMapping("deals")
    @Operation(summary = "부동산 임대차 거래 목록 조회",
            description = "보증금, 월세, 계약일, 대상 지역 등의 조건으로 부동산 매매 실거래가 정보를 조회한다.")
    public ResponseEntity<PageResponse<RealEstateLeaseSummaryDto>> getRealEstateLeaseDeals(
            @ParameterObject RealEstateLeaseRequestParameter requestParameter,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(
                PageResponse.from(
                        realEstateLeaseService.findRealEstateLeases(
                                RealEstateLeaseQuery
                                        .builder()
                                        .maxDeposit(requestParameter.maxDeposit())
                                        .minDeposit(requestParameter.minDeposit())
                                        .maxMonthlyRentFee(requestParameter.maxMonthlyRentFee())
                                        .minMonthlyRentFee(requestParameter.minMonthlyRentFee())
                                        .maxNetLeasableArea(requestParameter.maxNetLeasableArea())
                                        .minNetLeasableArea(requestParameter.minNetLeasableArea())
                                        .startYearMonth(requestParameter.startYearMonth())
                                        .endYearMonth(requestParameter.endYearMonth())
                                        .targetRegionIds(requestParameter.targetRegionIds())
                                        .targetHousingTypes(requestParameter.targetHousingTypes())
                                        .build(),
                                pageable
                        )
                )
        );
    }

}

