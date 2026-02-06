package com.bb.eodi.deal.application.service;


import com.bb.eodi.common.model.Cursor;
import com.bb.eodi.common.model.CursorRequest;
import com.bb.eodi.deal.application.input.FindRecommendedLeaseInput;
import com.bb.eodi.deal.application.input.FindRecommendedRegionInput;
import com.bb.eodi.deal.application.input.FindRecommendedSellInput;
import com.bb.eodi.deal.application.result.RealEstateLeaseSummaryResult;
import com.bb.eodi.deal.application.result.RealEstateSellSummaryResult;
import com.bb.eodi.deal.application.result.RecommendedRegionsResult;
import com.bb.eodi.deal.application.result.RegionItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Transactional
@DisplayName("medium - 부동산 추천 서비스 애플리케이션 레이어 medium test")
@SpringBootTest
class RealEstateRecommendationServiceMediumTest {

    @Autowired
    RealEstateRecommendationService realEstateRecommendationService;

    @Test
    @DisplayName("medium - 매매 지역 추천조회 지역별 count와 매매 데이터 추천조회 전체 count 수가 동일한지 확인한다.")
    void testSellRegionAndDataCount() throws Exception {
        // given
        // cash=12121&housingTypes=AP&housingTypes=OF
        Long cash = 12121L;
        List<String> housingTypes = List.of("AP", "OF");

        FindRecommendedRegionInput regionInput = new FindRecommendedRegionInput(cash, null, null, null, null, housingTypes);
        RecommendedRegionsResult regionsResult = realEstateRecommendationService.findRecommendedSellRegions(regionInput);
        List<RegionItem> regionItems = regionsResult.regions().get("1100000000").subList(0, 5);

        // when
        FindRecommendedSellInput sellInput = new FindRecommendedSellInput(cash, regionItems.stream().map(RegionItem::id).toList(), housingTypes, null, null, null, null, null, null, null, null);
        boolean hasNext = true;
        Long nextId = null;
        int pageSize = 500;

        int count = 0;

        while(hasNext) {
            Cursor<RealEstateSellSummaryResult> result = realEstateRecommendationService.findRecommendedSells(sellInput, new CursorRequest(nextId, pageSize));
            count += result.content().size();

            hasNext = result.hasNext();
            nextId = result.nextId();
        }

        // then
        Assertions
                .assertThat(regionItems
                        .stream()
                        .map(RegionItem::count)
                        .reduce(0, Integer::sum))
                .isEqualTo(count);
    }

    @Test
    @DisplayName("medium - 입력된 파라미터 기반으로 추천 매매 지역 목록을 리턴한다.")
    void testFindRecommendedRegions() throws Exception {
        // given
        Long cash = 50000L;
        Boolean hasLoan = false;
        List<String> housingTypes = List.of("AP");

        // when
        RecommendedRegionsResult recommendedRegions = realEstateRecommendationService.findRecommendedSellRegions(
                new FindRecommendedRegionInput(cash, null,
                        null,
                        null,
                        null,
                        housingTypes));

        // then
        Assertions.assertThat(recommendedRegions.regions()).isNotEmpty();
        Assertions.assertThat(recommendedRegions.regionGroups()).isNotEmpty();
    }


    @Test
    @DisplayName("medium - 입력된 파라미터 기반으로 추천 임대차 데이터 목록을 리턴한다.")
    void testFindRecommendedLease() throws Exception {
        // given

        FindRecommendedLeaseInput input = new FindRecommendedLeaseInput(
                22121,
                List.of(95L, 2L),
                List.of("AP", "OF"),
                null,
                null,
                null,
                null,
                null,
                null
        );
        // when
        Page<RealEstateLeaseSummaryResult> result = realEstateRecommendationService.findRecommendedLeases(input, PageRequest.of(0, 20));

        // then
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(
                result.getContent().stream().map(RealEstateLeaseSummaryResult::getNaverUrl)
                        .anyMatch(url -> StringUtils.hasText(url))
        ).isTrue();
    }
}