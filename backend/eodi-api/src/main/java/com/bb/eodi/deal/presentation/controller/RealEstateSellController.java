package com.bb.eodi.deal.presentation.controller;

import com.bb.eodi.common.presentation.response.PageResponse;
import com.bb.eodi.deal.application.dto.RealEstateSellSummaryDto;
import com.bb.eodi.deal.presentation.dto.request.RealEstateSellRequestParameter;
import com.bb.eodi.deal.application.service.RealEstateSellService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 부동산 매매 실거래가 데이터 조회 controller
 */
@Tag(name = "부동산 매매 실거래가", description = "부동산 매매 실거래가 데이터 API")
@Slf4j
@RestController
@RequestMapping("real-estate/sell")
@RequiredArgsConstructor
public class RealEstateSellController {

    private final RealEstateSellService realEstateSellService;

    /**
     * 부동산 매매 거래 목록 Page 조회
     * @param requestParameter API 요청 파라미터
     * @param pageable paging 파라미터
     * @return 부동산 매매 거래 목록 Page
     */
    @Operation(summary = "부동산 매매 거래 목록 조회",
            description = "거래 가격, 계약일, 대상 지역 등의 조건으로 부동산 매매 실거래가 정보를 조회한다.")
    @GetMapping("deals")
    public ResponseEntity<PageResponse<RealEstateSellSummaryDto>> getRecentRealEstateSells(
            @ParameterObject RealEstateSellRequestParameter requestParameter,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(
                PageResponse.from(
                        realEstateSellService.findRealEstateSells(
                                requestParameter,
                                pageable
                        )
                )
        );
    }
}
