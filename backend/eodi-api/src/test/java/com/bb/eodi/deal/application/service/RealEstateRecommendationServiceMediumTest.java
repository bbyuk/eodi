package com.bb.eodi.deal.application.service;


import com.bb.eodi.deal.application.input.FindRecommendedLeaseInput;
import com.bb.eodi.deal.application.input.FindRecommendedRegionInput;
import com.bb.eodi.deal.application.input.FindRecommendedSellInput;
import com.bb.eodi.deal.application.result.RealEstateLeaseSummaryResult;
import com.bb.eodi.deal.application.result.RealEstateSellSummaryResult;
import com.bb.eodi.deal.application.result.RecommendedRegionsResult;
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
    @DisplayName("medium - 입력된 파라미터 기반으로 추천 지역 목록을 리턴한다.")
    void testFindRecommendedRegions() throws Exception {
        // given
        Long cash = 50000L;
        Boolean hasLoan = false;
        List<String> housingTypes = List.of("AP");

        // when
        RecommendedRegionsResult recommendedRegions = realEstateRecommendationService.findRecommendedRegions(
                new FindRecommendedRegionInput(cash, null,
                        null,
                        null,
                        null,
                        housingTypes));

        // then
        Assertions.assertThat(recommendedRegions.leaseRegions()).isNotEmpty();
        Assertions.assertThat(recommendedRegions.sellRegions()).isNotEmpty();
        Assertions.assertThat(recommendedRegions.sellRegionGroups()).isNotEmpty();
        Assertions.assertThat(recommendedRegions.leaseRegionGroups()).isNotEmpty();
    }

    @Test
    @DisplayName("medium - 입력된 파라미터 기반으로 추천 매매 데이터 목록을 리턴한다.")
    void testFindRecommendedSell() throws Exception {
        // given
        FindRecommendedSellInput input = new FindRecommendedSellInput(
                50000L,
                List.of(14261L, 14306L),
                List.of("AP", "OF"),
                null,
                null,
                null,
                null);

        // when
        Page<RealEstateSellSummaryResult> allResult =
                realEstateRecommendationService.findRecommendedSells(
                        input,
                        PageRequest.of(1, 20));

        // then
        Assertions.assertThat(allResult).isNotEmpty();
        Assertions.assertThat(
                allResult.getContent().stream().map(RealEstateSellSummaryResult::getNaverUrl)
                        .anyMatch(url -> StringUtils.hasText(url))
        ).isTrue();
    }


    @Test
    @DisplayName("medium - 입력된 파라미터 기반으로 추천 임대차 데이터 목록을 리턴한다.")
    void testFindRecommendedLease() throws Exception {
        // given

        FindRecommendedLeaseInput input = new FindRecommendedLeaseInput(
                22121,
                List.of(14363L, 14415L, 14584L),
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