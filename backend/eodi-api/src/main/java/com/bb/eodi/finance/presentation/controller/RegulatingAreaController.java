package com.bb.eodi.finance.presentation.controller;

import com.bb.eodi.finance.application.service.RegulatingAreaService;
import com.bb.eodi.finance.presentation.dto.request.RegulatingAreaRegisterRequest;
import com.bb.eodi.finance.presentation.dto.response.RegulatingAreaRegisterResponse;
import com.bb.eodi.finance.presentation.mapper.RegulatingAreaRegisterMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 규제지역 API 컨트롤러
 */
@Tag(name = "부동산 규제지역 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("real-estate/regulating-area")
public class RegulatingAreaController {

    private final RegulatingAreaService regulatingAreaService;
    private final RegulatingAreaRegisterMapper regulatingAreaRegisterMapper;


    @PostMapping
    @Operation(summary = "부동산 규제지역 등록",
            description = "대상 지역명을 입력해 부동산 규제지역을 등록한다.")
    public ResponseEntity<RegulatingAreaRegisterResponse> registerRegulatingArea(
            @ParameterObject @Valid
            @RequestBody
            RegulatingAreaRegisterRequest requestParameter
    ) {

        return ResponseEntity.ok(
                regulatingAreaRegisterMapper.toResponse(
                        regulatingAreaService.register(
                                regulatingAreaRegisterMapper.toInput(requestParameter)
                        )
                )
        );
    }

}
