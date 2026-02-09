package com.bb.eodi.legaldong.presentation.controller;

import com.bb.eodi.legaldong.application.service.LegalDongService;
import com.bb.eodi.legaldong.presentation.adapter.RegionLegalDongFindRequestAdapter;
import com.bb.eodi.legaldong.presentation.dto.request.RegionLegalDongFindRequest;
import com.bb.eodi.legaldong.presentation.dto.response.LegalDongFindResponse;
import com.bb.eodi.legaldong.presentation.mapper.LegalDongFindResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "한국 법정동 데이터 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("legal-dong")
public class LegalDongController {

    private final LegalDongService legalDongService;
    private final LegalDongFindResponseMapper legalDongFindResponseMapper;
    private final RegionLegalDongFindRequestAdapter regionLegalDongFindRequestAdapter;

    @GetMapping("root")
    @Operation(summary = "최상위 법정동 (시도) 목록 조회")
    public ResponseEntity<LegalDongFindResponse> findRootLegalDongs() {
        return ResponseEntity.ok(
            legalDongFindResponseMapper.toResponse(
                    legalDongService.findAllRootLegalDongs()
            )
        );
    }

    @GetMapping("region")
    @Operation(summary = "대상 시도에 대한 지역 단위 법정동 (시군구) 목록 조회")
    public ResponseEntity<LegalDongFindResponse> findRegionLegalDongs(
            @ParameterObject @Valid
            RegionLegalDongFindRequest requestParameter
    ) {
        return ResponseEntity.ok(
                legalDongFindResponseMapper.toResponse(
                        legalDongService.findAllRegionLegalDongs(
                                regionLegalDongFindRequestAdapter.toInput(requestParameter)
                        )
                )
        );
    }

}
