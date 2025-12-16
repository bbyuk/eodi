package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.dto.RealEstateLeaseSummaryDto;
import com.bb.eodi.deal.application.dto.RealEstateLeaseSummaryDtoMapper;
import com.bb.eodi.deal.presentation.dto.request.RealEstateLeaseRequestParameter;
import com.bb.eodi.deal.domain.dto.RealEstateLeaseQuery;
import com.bb.eodi.deal.domain.repository.RealEstateLeaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 부동산 임대차 실거래가 API 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RealEstateLeaseService {

    private final RealEstateLeaseRepository realEstateLeaseRepository;
    private final RealEstateLeaseSummaryDtoMapper realEstateLeaseSummaryDtoMapper;

    /**
     * 부동산 임대차 데이터 조회
     * @param requestParameter 부동산 임대차 데이터 조회 요청 파라미터
     * @param pageable pageable 객체
     * @return 부동산 임대차 데이터 목록
     */
    @Transactional
    public Page<RealEstateLeaseSummaryDto> findRealEstateLeases(RealEstateLeaseRequestParameter requestParameter, Pageable pageable) {
        return realEstateLeaseRepository.findBy(
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
                        .build(), pageable)
                .map(realEstateLeaseSummaryDtoMapper::toDto);
    }
}
