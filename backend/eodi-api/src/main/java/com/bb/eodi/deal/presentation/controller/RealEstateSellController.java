package com.bb.eodi.deal.presentation.controller;

import com.bb.eodi.deal.application.service.RealEstateSellService;
import com.bb.eodi.deal.domain.dto.RealEstateSellQuery;
import com.bb.eodi.deal.presentation.request.RealEstateSellRequestParameter;
import com.bb.eodi.deal.presentation.response.RecentRealEstateSellResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 부동산 매매 실거래가 데이터 조회 controller
 */
@Slf4j
@RestController
@RequestMapping("real-estate/sell")
@RequiredArgsConstructor
public class RealEstateSellController {

    private final RealEstateSellService realEstateSellService;

    @GetMapping("recent/deals")
    public ResponseEntity<RecentRealEstateSellResponse> getRecentRealEstateSells(RealEstateSellRequestParameter requestParameter) {
        return ResponseEntity.ok(
                new RecentRealEstateSellResponse(
                        realEstateSellService.findRealEstateSells(
                                RealEstateSellQuery
                                        .builder()
                                        .maxPrice(requestParameter.maxPrice())
                                        .minPrice(requestParameter.minPrice())
                                        .floor(requestParameter.floor())
                                        .housingType(requestParameter.housingType())
                                        .build()
                        ),
                        0,
                        0,
                        0
                )
        );
    }
}
