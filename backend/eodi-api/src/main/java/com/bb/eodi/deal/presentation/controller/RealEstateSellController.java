package com.bb.eodi.deal.presentation.controller;

import com.bb.eodi.common.presentation.response.PageResponse;
import com.bb.eodi.deal.application.dto.RealEstateSellSummaryDto;
import com.bb.eodi.deal.application.service.RealEstateSellService;
import com.bb.eodi.deal.domain.dto.RealEstateSellQuery;
import com.bb.eodi.deal.presentation.request.RealEstateSellRequestParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 부동산 매매 실거래가 데이터 조회 controller
 */
@Slf4j
@RestController
@RequestMapping("real-estate/sell")
@RequiredArgsConstructor
public class RealEstateSellController {

    private final RealEstateSellService realEstateSellService;

    /**
     * 최근 부동산 실거래가 Page 조회
     * @param requestParameter API 요청 파라미터
     * @param pageable paging 파라미터
     * @return 최근 부동산 실거래가 PageResponse
     */
    @GetMapping("recent/deals")
    public ResponseEntity<PageResponse<RealEstateSellSummaryDto>> getRecentRealEstateSells(RealEstateSellRequestParameter requestParameter, Pageable pageable) {
        return ResponseEntity.ok(
                PageResponse.from(
                        realEstateSellService.findRealEstateSells(
                                RealEstateSellQuery
                                        .builder()
                                        .maxPrice(requestParameter.maxPrice())
                                        .minPrice(requestParameter.minPrice())
                                        .floor(requestParameter.floor())
                                        .housingType(requestParameter.housingType())
                                        .build(),
                                pageable
                        )
                )
        );
    }
}
