package com.bb.eodi.deal.presentation.controller;

import com.bb.eodi.common.presentation.response.PageResponse;
import com.bb.eodi.deal.application.dto.RealEstateLeaseSummaryDto;
import com.bb.eodi.deal.application.service.RealEstateLeaseService;
import com.bb.eodi.deal.domain.dto.RealEstateLeaseQuery;
import com.bb.eodi.deal.presentation.request.RealEstateLeaseRequestParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 부동산 임대차 실거래가 데이터 API 컨트롤러
 */
@RequestMapping("real-estate/lease")
@RestController
@RequiredArgsConstructor
public class RealEstateLeaseController {

    private final RealEstateLeaseService realEstateLeaseService;

    /**
     * 부동산 임대차 거래 목록 페이징 조회
     * @param requestParameter 요청 파라미터 dto
     * @param pageable 페이징 dto
     * @return 부동산 임대차 거래 목록 페이지
     */
    @GetMapping("recent/deals")
    public ResponseEntity<PageResponse<RealEstateLeaseSummaryDto>> getRealEstateLeaseDeals(
            RealEstateLeaseRequestParameter requestParameter, Pageable pageable) {

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
                                        .targetRegionIds(requestParameter.targetRegionIds())
                                        .targetHousingTypes(requestParameter.targetHousingTypes())
                                        .build(),
                                pageable
                        )
                )
        );
    }

}

