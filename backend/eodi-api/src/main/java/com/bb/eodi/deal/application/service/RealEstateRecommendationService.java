package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.dto.RecommendedRegionsDto;
import com.bb.eodi.deal.application.dto.RegionDto;
import com.bb.eodi.deal.application.dto.RegionGroupDto;
import com.bb.eodi.deal.application.model.LegalDongInfo;
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
import java.util.Map;
import java.util.stream.Collectors;

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
     * <p>
     * 1. 보유 현금
     * 매매는 매매가 기준 +- 5000만원 / 임대차는 보증금 기준 +- 1000
     * 최근 3개월 거래내역 확인
     *
     * @param cash 입력된 보유 현금
     * @return 추천 지역 목록
     */
    @Transactional(readOnly = true)
    public RecommendedRegionsDto findRecommendedRegions(Integer cash) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(monthsToView);

        List<Region> allSellRegions = realEstateSellRepository.findRegionsBy(
                RegionQuery.builder()
                        .minCash(cash - sellPriceGap)
                        .maxCash(cash + sellPriceGap)
                        .startDate(startDate)
                        .endDate(today)
                        .build()
        );

        List<Region> allLeaseRegions = realEstateLeaseRepository.findRegionsBy(
                RegionQuery.builder()
                        .minCash(cash - leaseDepositGap)
                        .maxCash(cash + leaseDepositGap)
                        .startDate(startDate)
                        .endDate(today)
                        .build()
        );

        Map<Long, List<Region>> sellRegionGroups = allSellRegions.stream()
                .collect(Collectors.groupingBy(Region::getRootId));

        Map<Long, List<Region>> sellRegions = allSellRegions.stream()
                .collect(Collectors.groupingBy(region -> region.isRoot() ? region.getRootId() : region.getSecondId()));

        Map<Long, List<Region>> leaseRegionGroups = allLeaseRegions.stream()
                .collect(Collectors.groupingBy(Region::getRootId));

        Map<Long, List<Region>> leaseRegions = allLeaseRegions.stream()
                .collect(Collectors.groupingBy(region -> region.isRoot() ? region.getRootId() : region.getSecondId()));

        return new RecommendedRegionsDto(
                sellRegionGroups.entrySet()
                        .stream()
                        .map(entry -> {
                            LegalDongInfo rootLegalDongInfo = legalDongCachePort.findById(entry.getKey());
                            return new RegionGroupDto(
                                    rootLegalDongInfo.code(),
                                    rootLegalDongInfo.name(),
                                    // TODO name 정제 로직 필요
                                    rootLegalDongInfo.name(),
                                    entry.getValue().size()
                            );
                        })
                        .collect(Collectors.groupingBy(RegionGroupDto::code)),
                sellRegions.entrySet()
                        .stream()
                        .map(entry -> {
                            LegalDongInfo secondLegalDongInfo = legalDongCachePort.findById(entry.getKey());
                            LegalDongInfo rootLegalDongInfo = legalDongCachePort.findById(secondLegalDongInfo.rootId());
                            return new RegionDto(
                                    rootLegalDongInfo.code(),
                                    secondLegalDongInfo.code(),
                                    secondLegalDongInfo.name(),
                                    // TODO name 정제 로직 필요
                                    secondLegalDongInfo.name(),
                                    entry.getValue().size()
                            );
                        })
                        .collect(Collectors.groupingBy(RegionDto::groupCode)),
                leaseRegionGroups.entrySet()
                        .stream()
                        .map(entry -> {
                            LegalDongInfo rootLegalDongInfo = legalDongCachePort.findById(entry.getKey());
                            return new RegionGroupDto(
                                    rootLegalDongInfo.code(),
                                    rootLegalDongInfo.name(),
                                    // TODO name 정제 로직 필요
                                    rootLegalDongInfo.name(),
                                    entry.getValue().size()
                            );
                        })
                        .collect(Collectors.groupingBy(RegionGroupDto::code)),
                leaseRegions.entrySet()
                        .stream()
                        .map(entry -> {
                            LegalDongInfo secondLegalDongInfo = legalDongCachePort.findById(entry.getKey());
                            LegalDongInfo rootLegalDongInfo = legalDongCachePort.findById(secondLegalDongInfo.rootId());

                            return new RegionDto(
                                    rootLegalDongInfo.code(),
                                    secondLegalDongInfo.code(),
                                    secondLegalDongInfo.name(),
                                    // TODO name 정제 로직 필요
                                    secondLegalDongInfo.name(),
                                    entry.getValue().size()
                            );
                        })
                        .collect(Collectors.groupingBy(RegionDto::groupCode))
                );
    }
}
