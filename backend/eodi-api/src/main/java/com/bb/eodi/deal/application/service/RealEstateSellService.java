package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.dto.RealEstateSellSummaryDto;
import com.bb.eodi.deal.application.dto.RealEstateSellSummaryDtoMapper;
import com.bb.eodi.deal.application.dto.request.RealEstateSellRequestParameter;
import com.bb.eodi.deal.application.port.LegalDongCachePort;
import com.bb.eodi.deal.domain.dto.RealEstateSellQuery;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 부동산 매매 데이터 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RealEstateSellService {

    private final RealEstateSellRepository realEstateSellRepository;
    private final RealEstateSellSummaryDtoMapper summaryDtoMapper;
    private final LegalDongCachePort legalDongCachePort;
    private final RealEstateSellSummaryDtoMapper realEstateSellSummaryDtoMapper;


    /**
     * 부동산 매재 데이터 조회
     * @param requestParameter 부동산 매매 데이터 조회 요청 파라미터
     * @param pageable pageable 객체
     * @return 부동산 매매 데이터 목록
     */
    @Transactional
    public Page<RealEstateSellSummaryDto> findRealEstateSells(
            @ParameterObject RealEstateSellRequestParameter requestParameter,
            @ParameterObject Pageable pageable) {
        return realEstateSellRepository.findBy(
                RealEstateSellQuery
                        .builder()
                        .maxPrice(requestParameter.maxPrice())
                        .minPrice(requestParameter.minPrice())
                        .maxNetLeasableArea(requestParameter.maxNetLeasableArea())
                        .minNetLeasableArea(requestParameter.minNetLeasableArea())
                        .startYearMonth(requestParameter.startYearMonth())
                        .endYearMonth(requestParameter.endYearMonth())
                        .targetHousingTypes(requestParameter.targetHousingTypes())
                        .targetRegionIds(requestParameter.targetRegionIds())
                        .build(), pageable)
                .map(realEstateSell -> {
                    RealEstateSellSummaryDto resultDto = realEstateSellSummaryDtoMapper.toDto(realEstateSell);

                    // legalDongFullName set -> 지역명 + 동명
                    resultDto.setLegalDongFullName(legalDongCachePort.findById(resultDto.getRegionId()) + " " + resultDto.getLegalDongName());
                    return resultDto;
                });
    }

}
