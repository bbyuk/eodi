package com.bb.eodi.deal.presentation.controller;

import com.bb.eodi.deal.application.service.RealEstateDealCommonService;
import com.bb.eodi.deal.presentation.dto.response.HousingTypeCodeResponse;
import com.bb.eodi.deal.presentation.mapper.HousingTypeCodeResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * deal 도메인의 코드 조회 API
 */
@Tag(name = "부동산 실거래가 도메인 코드 조회 API")
@RestController
@RequestMapping("real-estate/code")
@RequiredArgsConstructor
public class RealEstateDealCodeController {

    private final RealEstateDealCommonService realEstateDealCommonService;

    private final HousingTypeCodeResponseMapper housingTypeCodeResponseMapper;

    @GetMapping("housing-type")
    @Operation(summary = "부동산 실거래가 데이터 제공 주택 유형 코드 목록 조회",
            description = "부동산 실거래가 데이터 조회가 가능한 주택 유형 코드 목록을 조회한다.")
    public ResponseEntity<HousingTypeCodeResponse> getAllHousingTypeCodes() {
        return ResponseEntity.ok(
                housingTypeCodeResponseMapper.toResponse(
                        realEstateDealCommonService.findAllHousingTypeCodes()
                )
        );
    }
}
