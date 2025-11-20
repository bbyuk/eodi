package com.bb.eodi.deal.application.service;


import com.bb.eodi.deal.application.dto.RecommendedRegionsDto;
import com.bb.eodi.deal.application.dto.request.RegionRecommendRequest;
import com.bb.eodi.deal.domain.type.HousingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@DisplayName("medium - 부동산 추천 서비스 애플리케이션 레이어 medium test")
@SpringBootTest
class RealEstateRecommendationServiceMediumTest {

    @Autowired
    RealEstateRecommendationService realEstateRecommendationService;


    @Test
    @DisplayName("medium - 입력된 파라미터 기반으로 추천 지역 목록을 리턴한다.")
    void testFindRecommendedRegions() throws Exception {
        // given
        Integer cash = 50000;
        List<String> housingTypes = List.of("AP");

        // when
        RecommendedRegionsDto recommendedRegions = realEstateRecommendationService.findRecommendedRegions(new RegionRecommendRequest(cash, housingTypes));

        // then
        Assertions.assertThat(recommendedRegions.leaseRegions()).isNotEmpty();
        Assertions.assertThat(recommendedRegions.sellRegions()).isNotEmpty();
        Assertions.assertThat(recommendedRegions.sellRegionGroups()).isNotEmpty();
        Assertions.assertThat(recommendedRegions.leaseRegionGroups()).isNotEmpty();
    }
}