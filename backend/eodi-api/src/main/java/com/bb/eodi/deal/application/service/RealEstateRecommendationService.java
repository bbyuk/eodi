package com.bb.eodi.deal.application.service;

import com.bb.eodi.common.model.Cursor;
import com.bb.eodi.common.model.CursorRequest;
import com.bb.eodi.deal.application.contract.LegalDongInfo;
import com.bb.eodi.deal.application.contract.mapper.LegalDongInfoMapper;
import com.bb.eodi.deal.application.input.FindRecommendedLeaseInput;
import com.bb.eodi.deal.application.input.FindRecommendedRegionInput;
import com.bb.eodi.deal.application.input.FindRecommendedSellInput;
import com.bb.eodi.deal.application.port.DealFinancePort;
import com.bb.eodi.deal.application.port.DealLegalDongCachePort;
import com.bb.eodi.deal.application.port.RealEstatePlatformUrlGeneratePort;
import com.bb.eodi.deal.application.result.*;
import com.bb.eodi.deal.application.result.mapper.RealEstateLeaseSummaryResultMapper;
import com.bb.eodi.deal.application.result.mapper.RealEstateSellSummaryResultMapper;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.entity.Region;
import com.bb.eodi.deal.domain.query.RealEstateLeaseQuery;
import com.bb.eodi.deal.domain.query.RealEstateSellQuery;
import com.bb.eodi.deal.domain.query.RealEstateSellRecommendQuery;
import com.bb.eodi.deal.domain.query.RegionQuery;
import com.bb.eodi.deal.domain.read.RegionCandidate;
import com.bb.eodi.deal.domain.repository.RealEstateLeaseRepository;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import com.bb.eodi.deal.domain.type.HousingType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 부동산 추천 서비스
 */
@Service
@RequiredArgsConstructor
public class RealEstateRecommendationService {

    private final RealEstateSellRepository realEstateSellRepository;
    private final RealEstateLeaseRepository realEstateLeaseRepository;

    private final RealEstateSellSummaryResultMapper realEstateSellSummaryResultMapper;
    private final RealEstateLeaseSummaryResultMapper realEstateLeaseSummaryResultMapper;
    private final LegalDongInfoMapper legalDongInfoMapper;

    private final ObjectMapper objectMapper;

    private final RealEstatePlatformUrlGeneratePort realEstatePlatformUrlGeneratePort;
    private final DealLegalDongCachePort dealLegalDongCachePort;
    private final DealFinancePort dealFinancePort;

    // 매매 가격 gap
    private final long sellPriceGap = 5000L;

    // 임대차 보증금 gap
    private final long leaseDepositGap = 1000L;

    // 조회 범위
    private final int monthsToView = 3;

    // 지역 추천 조회시 최소 거래 횟수 카운트
    private final int minDealCount = 3;


    /**
     * 입력된 파라미터 기반으로 맞춤 지역 목록을 리턴한다.
     * <p>
     * 1. 매매
     * 매매가 기준 - 5000만원 ~ 보유 현금 + 주택담보대출 가능 금액
     * 2. 임대차
     * 보증금 + 전세대출 가능 금액
     *
     * <p>
     * 최근 3개월 거래내역 확인
     *
     * @param input 추천지역조회 application Input
     * @return 추천 지역 목록
     */
    @Transactional(readOnly = true)
    public RecommendedRegionsResult findRecommendedSellRegions(FindRecommendedRegionInput input) {
        LocalDate today = LocalDate.now();
        LocalDate theDayBeforeThreeMonths = today.minusMonths(monthsToView);

        Set<String> housingTypeSet = new HashSet<>(input.housingTypes());
        if (housingTypeSet.contains(HousingType.APT.code())) {
            // 아파트 선택되었을 경우 분양권/입주권도 함꼐 추가
            housingTypeSet.add(HousingType.PRESALE_RIGHT.code());
            housingTypeSet.add(HousingType.OCCUPY_RIGHT.code());
        } else if (housingTypeSet.contains(HousingType.DETACHED_HOUSE.code())) {
            housingTypeSet.add(HousingType.MULTI_UNIT_HOUSE.code());
        }

        Stream<RegionCandidate> regionCandidateStream = realEstateSellRepository.findAllRegionCandidates()
                .filter(regionCandidate -> {
                    long availableMortgageLoanAmount =
                            dealFinancePort.calculateAvailableMortgageLoanAmount(
                                    input.annualIncome(),
                                    input.monthlyPayment(),
                                    input.isFirstTimeBuyer(),
                                    regionCandidate.regionId(),
                                    regionCandidate.price()
                            );

                    boolean isBetweenTargetDuration =
                            (regionCandidate.contractDate().isEqual(theDayBeforeThreeMonths) || regionCandidate.contractDate().isAfter(theDayBeforeThreeMonths))
                                    && (regionCandidate.contractDate().isEqual(today) || regionCandidate.contractDate().isBefore(today));

                    return regionCandidate.price() <= input.cash() + availableMortgageLoanAmount
                            && isBetweenTargetDuration;
                });


        Map<String, List<RegionItem>> sellRegionItems = toRegionItems(regionCandidateStream);
        Map<String, RegionGroupItem> sellRegionGroups = toRegionGroups(sellRegionItems);

        /**
         * sellRegions -> code 오름차순 정렬
         * leaseRegions -> code 오름차순 정렬
         */
        sellRegionItems.replaceAll((key, value) -> value.stream()
                .sorted(Comparator.comparing(RegionItem::code))
                .collect(Collectors.toList()));

        return new RecommendedRegionsResult(
                sellRegionGroups,
                sellRegionItems
        );
    }

    /**
     * 임대차 추천 지역 조회
     *
     * @param input 임대차 추천 지역 조회 input
     * @return 임대차 추천 지역 조회 결과
     */
    @Transactional(readOnly = true)
    public RecommendedRegionsResult findRecommendedLeaseRegions(FindRecommendedRegionInput input) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(monthsToView);

        Set<String> housingTypeSet = new HashSet<>(input.housingTypes());
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

        RegionQuery leaseRegionQuery = RegionQuery.builder()
                .minCash(input.cash() - leaseDepositGap)
                .maxCash(input.cash() + dealFinancePort.calculateAvailableDepositLoanAmount(
                        input.cash())
                )
                .startDate(startDate)
                .endDate(today)
                .housingTypes(housingTypeParameters)
                .minDealCount(minDealCount)
                .build();

        List<Region> allLeaseRegions = realEstateLeaseRepository.findRegionsBy(leaseRegionQuery);
        Map<String, List<RegionItem>> leaseRegionItems = toRegionItems(allLeaseRegions);
        Map<String, RegionGroupItem> leaseRegionGroups = toRegionGroups(leaseRegionItems);


        leaseRegionItems.replaceAll((key, value) -> value.stream()
                .sorted(Comparator.comparing(RegionItem::code))
                .collect(Collectors.toList()));

        return new RecommendedRegionsResult(
                leaseRegionGroups,
                leaseRegionItems
        );
    }

    /**
     * RegionItem Map으로 전환 편의 메서드
     * @param regions 집계 대상 지역 목록
     * @return 부모 코드 : 지역 목록 table
     */
    private Map<String, List<RegionItem>> toRegionItems(List<Region> regions) {
        return regions.stream().collect(Collectors.groupingBy(r -> r.isRoot() ? r.getRootId() : r.getSecondId())).entrySet().stream().map(e -> {
            LegalDongInfo second = dealLegalDongCachePort.findById(e.getKey());
            LegalDongInfo root = dealLegalDongCachePort.findById(second.rootId());
            return new RegionItem(second.id(), root.code(), second.code(), second.name(), second.name().replace(root.name(), "").trim());
        }).collect(Collectors.groupingBy(RegionItem::groupCode));
    }

    /**
     * RegionItem Map으로 전환 편의 메서드
     *
     * @param candidates 조회 결과 region 집계 후보군 stream
     * @return Region Item Map
     */
    private Map<String, List<RegionItem>> toRegionItems(Stream<RegionCandidate> candidates) {
        Map<Long, Long> regionCountMap =
                candidates.collect(Collectors.groupingBy(
                        RegionCandidate::regionId,
                        Collectors.counting()
                ));

        return regionCountMap.entrySet()
                .stream()
                .map(entry -> {
                    Long regionId = entry.getKey();

                    LegalDongInfo second = dealLegalDongCachePort.findById(regionId);
                    LegalDongInfo root = dealLegalDongCachePort.findById(second.rootId());

                    return new RegionItem(
                            second.id(),
                            root.code(),
                            second.code(),
                            second.name(),
                            second.name().replace(root.name(), "").trim()
                    );
                })
                .collect(Collectors.groupingBy(RegionItem::groupCode));
    }

    /**
     * RegionGroupItem Map으로 전환 편의 메서드
     *
     * @param regionItems 조회결과 List에서 집계한 Region item map
     * @return RegionGroup Item Map
     */
    private Map<String, RegionGroupItem> toRegionGroups(Map<String, List<RegionItem>> regionItems) {
        return regionItems.entrySet()
                .stream()
                .map(entry -> {
                    LegalDongInfo rootLegalDong = dealLegalDongCachePort.findByCode(entry.getKey());


                    return new RegionGroupItem(
                            rootLegalDong.code(),
                            rootLegalDong.name(),
                            rootLegalDong.name()
                    );
                })
                .collect(
                        Collectors.toMap(
                                RegionGroupItem::code,
                                Function.identity()
                        )
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
     * @param input         요청 파라미터
     * @param cursorRequest cursor 요청 파라미터 객체
     * @return 추천 매매 데이터 목록
     */
    @Transactional(readOnly = true)
    public Cursor<RealEstateSellSummaryResult> findRecommendedSells(FindRecommendedSellInput input, CursorRequest cursorRequest) {
        LocalDate today = LocalDate.now();
        LocalDate theDayBeforeThreeMonths = today.minusMonths(monthsToView);

        RealEstateSellRecommendQuery query = RealEstateSellRecommendQuery.builder()
                .maxPrice(input.maxPrice() != null
                        ? input.maxPrice()
                        : input.cash() + dealFinancePort.calculateMaximumMortgageLoanAmount(input.cash()
                ))
                .minPrice(input.minPrice() != null ? input.minPrice() : input.cash() - sellPriceGap)
                .targetRegionIds(input.targetRegionIds())
                .startDate(theDayBeforeThreeMonths)
                .endDate(today)
                .targetHousingTypes(
                        input.targetHousingTypes()
                                .stream()
                                .map(HousingType::fromCode)
                                .collect(Collectors.toList())
                )
                .maxNetLeasableArea(input.maxNetLeasableArea())
                .minNetLeasableArea(input.minNetLeasableArea())
                .build();


        List<RealEstateSellSummaryResult> result = new ArrayList<>();

        boolean hasNext = false;
        Long nextId = cursorRequest.nextId();

        while (result.size() < cursorRequest.size()) {

            List<RealEstateSell> slice =
                    realEstateSellRepository.findRecommendedSellSlices(query, nextId, cursorRequest.size());

            if (slice.isEmpty()) break;

            hasNext = slice.size() > cursorRequest.size();
            int limit = Math.min(slice.size(), cursorRequest.size());

            for (int i = 0; i < limit; i++) {
                RealEstateSell sell = slice.get(i);

                long availableLoan =
                        dealFinancePort.calculateAvailableMortgageLoanAmount(
                                input.annualIncome(),
                                input.monthlyPayment(),
                                input.isFirstTimeBuyer(),
                                sell.getRegionId(),
                                sell.getPrice());

                if (sell.getPrice() <= input.cash() + availableLoan) {
                    result.add(toSummary(sell));
                    if (result.size() == cursorRequest.size()) {
                        nextId = sell.getId();
                        break;
                    }
                }

                // 정책 통과 여부와 상관없이 커서 이동
                nextId = sell.getId();
            }

            if (!hasNext) break;
        }

        return new Cursor<>(
                result,
                hasNext ? nextId : null,
                hasNext,
                cursorRequest.size()
        );
    }

    private RealEstateSellSummaryResult toSummary(RealEstateSell realEstateSell) {
        RealEstateSellSummaryResult resultDto = realEstateSellSummaryResultMapper.toResult(realEstateSell);

        // 법정동 명 concat
        resultDto.setLegalDongFullName(
                dealLegalDongCachePort.findById(resultDto.getRegionId()).name() +
                        " " +
                        resultDto.getLegalDongName());

        // 전용면적 소숫점 밑 2자리로 반올림
        resultDto.setNetLeasableArea(resultDto.getNetLeasableArea().setScale(2, RoundingMode.HALF_UP));

        // 네이버 URL 생성
        resultDto.setNaverUrl(
                realEstatePlatformUrlGeneratePort.generate(realEstateSell)
        );

        return resultDto;
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
     * @param input    부동산 임대차 실거래가 데이터 조회 application input
     * @param pageable pageable 파라미터 객체
     * @return 추천 매매 데이터 목록
     */
    @Transactional(readOnly = true)
    public Page<RealEstateLeaseSummaryResult> findRecommendedLeases(FindRecommendedLeaseInput input, Pageable pageable) {
        // TODO 정책 / 대출 관련 로직 추가 필요

        return realEstateLeaseRepository.findBy(
                        RealEstateLeaseQuery.builder()
                                .targetRegionIds(input.targetRegionIds())
                                .maxDeposit(input.maxDeposit())
                                .minDeposit(input.minDeposit())
                                .maxMonthlyRentFee(input.maxMonthlyRentFee())
                                .minMonthlyRentFee(input.minMonthlyRentFee())
                                .targetHousingTypes(
                                        input.targetHousingTypes()
                                                .stream()
                                                .map(HousingType::fromCode)
                                                .collect(Collectors.toList())
                                )
                                .maxNetLeasableArea(input.maxNetLeasableArea())
                                .minNetLeasableArea(input.minNetLeasableArea())
                                .build(),
                        pageable
                )
                .map(realEstateLease -> {
                    RealEstateLeaseSummaryResult resultDto = realEstateLeaseSummaryResultMapper.toResult(realEstateLease);

                    // 법정동 명 concat
                    resultDto.setLegalDongFullName(
                            dealLegalDongCachePort.findById(resultDto.getRegionId()).name() +
                                    " " +
                                    resultDto.getLegalDongName());

                    // 전용면적 소숫점 밑 2자리로 반올림
                    resultDto.setNetLeasableArea(resultDto.getNetLeasableArea().setScale(2, RoundingMode.HALF_UP));

                    // 네이버 URL 생성
                    resultDto.setNaverUrl(
                            realEstatePlatformUrlGeneratePort.generate(realEstateLease)
                    );

                    return resultDto;
                });
    }

    /**
     * 지역 추천 조회 스트리밍처리
     * <p>
     * 페이지 조회에 따른 스트리밍 조회
     *
     * @param input 지역 추천 조회 application input
     */
    public void findRecommendedRegionsV2(ResponseBodyEmitter emitter, FindRecommendedRegionInput input) {
        LocalDate today = LocalDate.now();
        LocalDate theDayBeforeThreeMonths = today.minusMonths(monthsToView);

        Set<String> housingTypeSet = new HashSet<>(input.housingTypes());
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


        // 1. 동일 쿼리 파라미터로 페이지 조회
        // 2. 페이지 순회 돌면서 정책 반영 후 hit시 streaming 결과에 add
        //      1. region group set contains 체크 후 없을 시 streaming 결과에 add
        //      2. region set contains 체크 후 없을 시 streaming 결과에 add
        //      3. delta count 결과에 add
        // 3. 스트리밍 emit 후 2번으로

        /**
         * 2. 동일 쿼리 파라미터로 페이지 조회
         */
        boolean sellHasNext = true;
        Long sellNextId = null;
        int sellPageSize = 100;
        Set<Long> sellRegionGroupSet = new HashSet<>();
        Set<Long> sellRegionSet = new HashSet<>();
        long sellCandidateMaxPrice = input.cash() + dealFinancePort.calculateMaximumMortgageLoanAmount(input.cash());
        long sellCandidateMinPrice = input.cash() - sellPriceGap;

        while (true) {
            if (sellHasNext) {
                break;
            }
            RealEstateSellRecommendQuery query = RealEstateSellRecommendQuery.builder()
                    .maxPrice(sellCandidateMaxPrice)
                    .minPrice(sellCandidateMinPrice)
                    .startDate(theDayBeforeThreeMonths)
                    .endDate(today)
                    .targetHousingTypes(housingTypeParameters)
                    .build();

            List<RealEstateSell> sells = realEstateSellRepository.findRecommendedSellSlices(query, sellNextId, sellPageSize);

            //      1. region group set contains 체크 후 없을 시 streaming 결과에 add


            //      2. region set contains 체크 후 없을 시 streaming 결과에 add
            //      3. delta count 결과에 add

            sellHasNext = sells.size() <= sellPageSize;
            if (sellHasNext) {
                sellNextId = sells.get(sellPageSize).getId();
            }
        }
    }

    /**
     * Event 객체를 NDJSON으로 변환한다.
     *
     * @param event 이벤트 객체
     * @return NDSJON 값
     * @throws JsonProcessingException
     */
    private String toNdjson(Object event) throws JsonProcessingException {
        return objectMapper.writeValueAsString(event) + "\n";
    }
}
