package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.dto.RealEstateLeaseSummaryDto;
import com.bb.eodi.deal.application.dto.RealEstateLeaseSummaryDtoMapper;
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

    @Transactional
    public Page<RealEstateLeaseSummaryDto> findRealEstateLeases(RealEstateLeaseQuery query, Pageable pageable) {
        return realEstateLeaseRepository.findBy(query, pageable)
                .map(realEstateLeaseSummaryDtoMapper::toDto);
    }
}
