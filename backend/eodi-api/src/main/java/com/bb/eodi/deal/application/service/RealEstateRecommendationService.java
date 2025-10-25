package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.dto.RecommendedRegionsDto;
import com.bb.eodi.deal.application.port.LegalDongCachePort;
import com.bb.eodi.deal.domain.dto.RegionQuery;
import com.bb.eodi.deal.domain.entity.Region;
import com.bb.eodi.deal.domain.repository.RealEstateLeaseRepository;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 부동산 추천 서비스
 */
@Service
@RequiredArgsConstructor
public class RealEstateRecommendationService {

    private final RealEstateSellRepository realEstateSellRepository;
    private final RealEstateLeaseRepository realEstateLeaseRepository;
    private final LegalDongCachePort legalDongCachePort;

    private final int sellPriceGap = 5000;
    private final int leaseDepositGap = 1000;
    private final int monthsToView = 3;

    /**
     * 입력된 파라미터 기반으로 추천 지역 목록을 리턴한다.
     *
     * 1. 보유 현금
     * 매매는 매매가 기준 +- 5000만원 / 임대차는 보증금 기준 +- 1000
     * 최근 3개월 거래내역 확인
     *
     *
     * @param cash 입력된 보유 현금
     * @return 추천 지역 목록
     */
    @Transactional(readOnly = true)
    public RecommendedRegionsDto findRecommendedRegions(Integer cash) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(monthsToView);

        List<Region> sellRegions = realEstateSellRepository.findRegionsBy(
                RegionQuery.builder()
                        .minPrice(cash - sellPriceGap)
                        .maxPrice(cash + sellPriceGap)
                        .startDate(startDate)
                        .endDate(today)
                        .build()
        );

        List<Region> leaseRegions = realEstateLeaseRepository.findRegionsBy(
                RegionQuery.builder()
                        .minPrice(cash - leaseDepositGap)
                        .maxPrice(cash + leaseDepositGap)
                        .startDate(startDate)
                        .endDate(today)
                        .build()
        );

        return null;
    }
}
