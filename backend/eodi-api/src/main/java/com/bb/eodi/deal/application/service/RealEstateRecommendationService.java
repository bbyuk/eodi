package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.contract.LegalDongInfo;
import com.bb.eodi.deal.application.port.LegalDongCachePort;
import com.bb.eodi.deal.application.result.*;
import com.bb.eodi.deal.application.result.mapper.RealEstateLeaseSummaryResultMapper;
import com.bb.eodi.deal.application.result.mapper.RealEstateSellSummaryResultMapper;
import com.bb.eodi.deal.application.util.NaverUrlGenerator;
import com.bb.eodi.deal.domain.dto.RealEstateSellQuery;
import com.bb.eodi.deal.domain.dto.RegionQuery;
import com.bb.eodi.deal.domain.entity.Region;
import com.bb.eodi.deal.domain.repository.RealEstateLeaseRepository;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.presentation.dto.request.RealEstateLeaseRecommendRequestParameter;
import com.bb.eodi.deal.presentation.dto.request.RealEstateSellRecommendRequestParameter;
import com.bb.eodi.deal.presentation.dto.request.RegionRecommendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
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

    private final RealEstateSellSummaryResultMapper realEstateSellSummaryResultMapper;
    private final RealEstateLeaseSummaryResultMapper realEstateLeaseSummaryResultMapper;

    private String naverPayBaseUrl = "https://m.land.naver.com/map";
    private int naverPayMapLev = 14;

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
     * @param requestParameter 요청 파라미터
     * @return 추천 지역 목록
     */
    @Transactional(readOnly = true)
    public RecommendedRegionsResult findRecommendedRegions(RegionRecommendRequest requestParameter) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(monthsToView);

        Set<String> housingTypeSet = new HashSet<>(requestParameter.housingTypes());
        if (housingTypeSet.contains(HousingType.APT.code())) {
            // 아파트 선택되었을 경우 분양권/입주권도 함꼐 추가
            housingTypeSet.add(HousingType.PRESALE_RIGHT.code());
            housingTypeSet.add(HousingType.OCCUPY_RIGHT.code());
        } else if (housingTypeSet.contains(HousingType.DETACHED_HOUSE.code())) {
            housingTypeSet.add(HousingType.MULTI_UNIT_HOUSE.code());
        }

        List<HousingType> housingTypeParameters = housingTypeSet.stream()
                .map(HousingType::fromCode)
                .collect(Collectors.toList());


        List<Region> allSellRegions = realEstateSellRepository.findRegionsBy(
                RegionQuery.builder()
                        .minCash(requestParameter.cash() - sellPriceGap)
                        .maxCash(requestParameter.cash() + sellPriceGap)
                        .startDate(startDate)
                        .endDate(today)
                        .housingTypes(
                                housingTypeParameters)
                        .build()
        );

        List<Region> allLeaseRegions = realEstateLeaseRepository.findRegionsBy(
                RegionQuery.builder()
                        .minCash(requestParameter.cash() - leaseDepositGap)
                        .maxCash(requestParameter.cash() + leaseDepositGap)
                        .startDate(startDate)
                        .endDate(today)
                        .housingTypes(
                                housingTypeParameters
                        )
                        .build()
        );

        Map<String, RecommendedRegionsResult.RegionGroup> sellRegionGroups = allSellRegions.stream()
                .collect(Collectors.groupingBy(Region::getRootId))
                .entrySet()
                .stream()
                .map(entry -> {
                    LegalDongInfo rootLegalDongInfo = legalDongCachePort.findById(entry.getKey());
                    return new RecommendedRegionsResult.RegionGroup(
                            rootLegalDongInfo.code(),
                            rootLegalDongInfo.name(),
                            rootLegalDongInfo.name(),
                            entry.getValue().size()
                    );
                })
                .collect(Collectors.toMap(
                        RecommendedRegionsResult.RegionGroup::code,
                        regionGroup -> regionGroup
                ));

        Map<String, List<RecommendedRegionsResult.RegionItem>> sellRegions = allSellRegions.stream()
                .collect(Collectors.groupingBy(region -> region.isRoot() ? region.getRootId() : region.getSecondId()))
                .entrySet()
                .stream()
                .map(entry -> {
                    LegalDongInfo secondLegalDongInfo = legalDongCachePort.findById(entry.getKey());
                    LegalDongInfo rootLegalDongInfo = legalDongCachePort.findById(secondLegalDongInfo.rootId());

                    return new RecommendedRegionsResult.RegionItem(
                            secondLegalDongInfo.id(),
                            rootLegalDongInfo.code(),
                            secondLegalDongInfo.code(),
                            secondLegalDongInfo.name(),
                            secondLegalDongInfo.name().replace(rootLegalDongInfo.name(), "").trim(),
                            entry.getValue().size()
                    );
                })
                .collect(Collectors.groupingBy(RecommendedRegionsResult.RegionItem::groupCode));


        Map<String, RecommendedRegionsResult.RegionGroup> leaseRegionGroups =  allLeaseRegions.stream()
                .collect(Collectors.groupingBy(Region::getRootId))
                .entrySet()
                .stream()
                .map(entry -> {
                    LegalDongInfo rootLegalDongInfo = legalDongCachePort.findById(entry.getKey());
                    return new RecommendedRegionsResult.RegionGroup(
                            rootLegalDongInfo.code(),
                            rootLegalDongInfo.name(),
                            rootLegalDongInfo.name(),
                            entry.getValue().size()
                    );
                })
                .collect(Collectors.toMap(
                        RecommendedRegionsResult.RegionGroup::code,
                        regionGroup -> regionGroup
                ));

        Map<String, List<RecommendedRegionsResult.RegionItem>> leaseRegions = allLeaseRegions.stream()
                .collect(Collectors.groupingBy(region -> region.isRoot() ? region.getRootId() : region.getSecondId()))
                .entrySet()
                .stream()
                .map(entry -> {
                    LegalDongInfo secondLegalDongInfo = legalDongCachePort.findById(entry.getKey());
                    LegalDongInfo rootLegalDongInfo = legalDongCachePort.findById(secondLegalDongInfo.rootId());

                    return new RecommendedRegionsResult.RegionItem(
                            secondLegalDongInfo.id(),
                            rootLegalDongInfo.code(),
                            secondLegalDongInfo.code(),
                            secondLegalDongInfo.name(),
                            secondLegalDongInfo.name().replace(rootLegalDongInfo.name(), "").trim(),
                            entry.getValue().size()
                    );
                })
                .collect(Collectors.groupingBy(RecommendedRegionsResult.RegionItem::groupCode));


        /**
         * sellRegions -> code 오름차순 정렬
         * leaseRegions -> code 오름차순 정렬
         */

        sellRegions.replaceAll((key, value) -> value.stream()
                .sorted(Comparator.comparing(RecommendedRegionsResult.RegionItem::code))
                .collect(Collectors.toList()));

        leaseRegions.replaceAll((key, value) -> value.stream()
                .sorted(Comparator.comparing(RecommendedRegionsResult.RegionItem::code))
                .collect(Collectors.toList()));

        return new RecommendedRegionsResult(
                sellRegionGroups,
                sellRegions,
                leaseRegionGroups,
                leaseRegions
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
     * @param pageable         pageable 파라미터 객체
     * @return 추천 매매 데이터 목록
     */
    @Transactional(readOnly = true)
    public Page<RealEstateSellSummaryResult> findRecommendedSells(RealEstateSellRecommendRequestParameter requestParameter, Pageable pageable) {
        RealEstateSellQuery realEstateSellQuery = RealEstateSellQuery.builder()
                .maxPrice(requestParameter.cash() + sellPriceGap)
                .minPrice(requestParameter.cash() - sellPriceGap)
                .targetRegionIds(requestParameter.targetRegionIds())
                .targetHousingTypes(
                        requestParameter.targetHousingTypes()
                                .stream()
                                .map(HousingType::fromCode)
                                .collect(Collectors.toList())
                )
                .maxNetLeasableArea(requestParameter.maxNetLeasableArea())
                .minNetLeasableArea(requestParameter.minNetLeasableArea())
                .build();

        // TODO 정책 / 대출 관련 로직 추가 필요

        return realEstateSellRepository.findBy(realEstateSellQuery, pageable)
                .map(realEstateSell -> {
                    RealEstateSellSummaryResult resultDto = realEstateSellSummaryResultMapper.toResult(realEstateSell);

                    // 법정동 명 concat
                    resultDto.setLegalDongFullName(
                            legalDongCachePort.findById(resultDto.getRegionId()).name() +
                                    " " +
                                    resultDto.getLegalDongName());

                    // 전용면적 소숫점 밑 2자리로 반올림
                    resultDto.setNetLeasableArea(resultDto.getNetLeasableArea().setScale(2, RoundingMode.HALF_UP));

                    // 네이버 URL 생성
                    resultDto.setNaverUrl(realEstateSell.createUrl(naverPayBaseUrl, naverPayMapLev));

                    return resultDto;
                });
    }

    /**
     * 입력된 파라미터 기반으로 맞춤 임대차 실거래 데이터 목록을 리턴한다.
     * <p>
     * 1. 보유 현금
     * <p>
     * 전세 -> 보증금 기준 +- +- 1000
     * 월세 ->
     *
     * <p>
     * 최근 3개월 거래내역 확인
     *
     * @param requestParameter 요청 파라미터
     * @param pageable         pageable 파라미터 객체
     * @return 추천 매매 데이터 목록
     */
    @Transactional(readOnly = true)
    public Page<RealEstateLeaseSummaryResult> findRecommendedLeases(RealEstateLeaseRecommendRequestParameter requestParameter, Pageable pageable) {
        // TODO 정책/ 대출 관련 로직 추가 필요
        return null;
    }
}
