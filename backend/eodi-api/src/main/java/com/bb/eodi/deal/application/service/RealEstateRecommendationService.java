package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.dto.*;
import com.bb.eodi.deal.application.dto.request.RealEstateLeaseRecommendRequestParameter;
import com.bb.eodi.deal.application.dto.request.RealEstateSellRecommendRequestParameter;
import com.bb.eodi.deal.application.model.LegalDongInfo;
import com.bb.eodi.deal.application.port.LegalDongCachePort;
import com.bb.eodi.deal.domain.dto.RealEstateSellQuery;
import com.bb.eodi.deal.domain.dto.RegionQuery;
import com.bb.eodi.deal.domain.entity.Region;
import com.bb.eodi.deal.domain.repository.RealEstateLeaseRepository;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import com.bb.eodi.deal.domain.type.HousingType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
     * 입력된 파라미터 기반으로 맞춤 지역 목록을 리턴한다.
     * <p>
     * 1. 보유 현금
     * 매매는 매매가 기준 +- 5000만원 / 임대차는 보증금 기준 +- 1000
     * <p>
     * 최근 3개월 거래내역 확인
     *
     * @param cash 입력된 보유 현금
     * @return 추천 지역 목록
     */
    @Transactional(readOnly = true)
    public RecommendedRegionsDto findRecommendedRegions(Integer cash, List<String> housingTypes) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(monthsToView);

        Set<String> housingTypeSet = new HashSet<>(housingTypes);
        if (housingTypeSet.contains(HousingType.APT.code())) {
            // 아파트 선택되었을 경우 분양권/입주권도 함꼐 추가
            housingTypeSet.add(HousingType.PRESALE_RIGHT.code());
            housingTypeSet.add(HousingType.OCCUPY_RIGHT.code());
        }
        else if (housingTypeSet.contains(HousingType.DETACHED_HOUSE.code())) {
            housingTypeSet.add(HousingType.MULTI_UNIT_HOUSE.code());
        }

        List<HousingType> housingTypeParameters = housingTypeSet.stream()
                .map(HousingType::fromCode)
                .collect(Collectors.toList());


        List<Region> allSellRegions = realEstateSellRepository.findRegionsBy(
                RegionQuery.builder()
                        .minCash(cash - sellPriceGap)
                        .maxCash(cash + sellPriceGap)
                        .startDate(startDate)
                        .endDate(today)
                        .housingTypes(
                                housingTypeParameters)
                        .build()
        );

        List<Region> allLeaseRegions = realEstateLeaseRepository.findRegionsBy(
                RegionQuery.builder()
                        .minCash(cash - leaseDepositGap)
                        .maxCash(cash + leaseDepositGap)
                        .startDate(startDate)
                        .endDate(today)
                        .housingTypes(
                                housingTypeParameters
                        )
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
                                    rootLegalDongInfo.name(),
                                    entry.getValue().size()
                            );
                        })
                        .collect(Collectors.toMap(
                                RegionGroupDto::code,
                                regionGroupDto -> regionGroupDto
                        )),
                sellRegions.entrySet()
                        .stream()
                        .map(entry -> {
                            LegalDongInfo secondLegalDongInfo = legalDongCachePort.findById(entry.getKey());
                            LegalDongInfo rootLegalDongInfo = legalDongCachePort.findById(secondLegalDongInfo.rootId());

                            return new RegionDto(
                                    rootLegalDongInfo.code(),
                                    secondLegalDongInfo.code(),
                                    secondLegalDongInfo.name(),
                                    secondLegalDongInfo.name().replace(rootLegalDongInfo.name(), "").trim(),
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
                                    rootLegalDongInfo.name(),
                                    entry.getValue().size()
                            );
                        })
                        .collect(Collectors.toMap(
                                RegionGroupDto::code,
                                regionGroupDto -> regionGroupDto
                        )),
                leaseRegions.entrySet()
                        .stream()
                        .map(entry -> {
                            LegalDongInfo secondLegalDongInfo = legalDongCachePort.findById(entry.getKey());
                            LegalDongInfo rootLegalDongInfo = legalDongCachePort.findById(secondLegalDongInfo.rootId());

                            return new RegionDto(
                                    rootLegalDongInfo.code(),
                                    secondLegalDongInfo.code(),
                                    secondLegalDongInfo.name(),
                                    secondLegalDongInfo.name().replace(rootLegalDongInfo.name(), "").trim(),
                                    entry.getValue().size()
                            );
                        })
                        .collect(Collectors.groupingBy(RegionDto::groupCode))
                );
    }


    /**
     * 입력된 파라미터 기반으로 맞춤 매매 실거래 데이터 목록을 리턴한다.
     * <p>
     * 1. 보유 현금
     * 매매는 매매가 기준 +- 5000만원
     * <p>
     * 최근 3개월 거래내역 확인
     * 
     * @param requestParameter 요청 파라미터
     * @param pageable pageable 파라미터 객체
     * @return 추천 매매 데이터 목록
     */
    @Transactional(readOnly = true)
    public Page<RealEstateSellSummaryDto> findRecommendedSells(RealEstateSellRecommendRequestParameter requestParameter, Pageable pageable) {




        return null;
    }

    /**
     * 입력된 파라미터 기반으로 맞춤 임대차 실거래 데이터 목록을 리턴한다.
     * <p>
     * 1. 보유 현금
     * 보증금 기준 +- 1000
     * <p>
     * 최근 3개월 거래내역 확인
     *
     * TODO 맞춤 임대차 데이터 페이징 조회 구현
     * @param requestParameter 요청 파라미터
     * @param pageable pageable 파라미터 객체
     * @return 추천 매매 데이터 목록
     */
    @Transactional(readOnly = true)
    public Page<RealEstateLeaseSummaryDto> findRecommendedLeases(RealEstateLeaseRecommendRequestParameter requestParameter, Pageable pageable) {
        return null;
    }
}
