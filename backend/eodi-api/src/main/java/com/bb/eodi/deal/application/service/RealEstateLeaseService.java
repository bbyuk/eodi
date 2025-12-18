package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.input.FindRealEstateLeaseInput;
import com.bb.eodi.deal.domain.query.RealEstateLeaseQuery;
import com.bb.eodi.deal.application.result.RealEstateLeaseSummaryResult;
import com.bb.eodi.deal.application.result.mapper.RealEstateLeaseSummaryResultMapper;
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
    private final RealEstateLeaseSummaryResultMapper realEstateLeaseSummaryResultMapper;

    /**
     * 부동산 임대차 데이터 조회
     * @param input 부동산 임대차 데이터 조회 요청 파라미터
     * @param pageable pageable 객체
     * @return 부동산 임대차 데이터 목록
     */
    @Transactional
    public Page<RealEstateLeaseSummaryResult> findRealEstateLeases(FindRealEstateLeaseInput input, Pageable pageable) {
        return realEstateLeaseRepository.findBy(
                RealEstateLeaseQuery
                        .builder()
                        .maxDeposit(input.maxDeposit())
                        .minDeposit(input.minDeposit())
                        .maxMonthlyRentFee(input.maxMonthlyRentFee())
                        .minMonthlyRentFee(input.minMonthlyRentFee())
                        .maxNetLeasableArea(input.maxNetLeasableArea())
                        .minNetLeasableArea(input.minNetLeasableArea())
                        .startYearMonth(input.startYearMonth())
                        .endYearMonth(input.endYearMonth())
                        .targetRegionIds(input.targetRegionIds())
                        .targetHousingTypes(input.targetHousingTypes())
                        .build(), pageable)
                .map(realEstateLeaseSummaryResultMapper::toResult);
    }
}
