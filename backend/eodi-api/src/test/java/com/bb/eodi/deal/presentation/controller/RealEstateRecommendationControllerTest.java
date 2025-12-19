package com.bb.eodi.deal.presentation.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("medium - 부동산 실거래가 추천 API 통합 테스트")
class RealEstateRecommendationControllerTest {

    String baseUrl = "/real-estate/recommendation";

    @Autowired
    MockMvc mockMvc;


    @Test
    @DisplayName("medium - 부동산 매매 추천 데이터 조회 API 통합 테스트")
    void testGetRecommendedRealEstateSells() throws Exception {
        String url = "/sells";

        mockMvc.perform(
                MockMvcRequestBuilders.get(baseUrl + url)
                        .param("cash", "50000")
                        .param("targetRegionIds", "14306")
                        .param("targetRegionIds", "14261")
                        .param("targetRegionIds", "14302")
                        .param("targetHousingTypes", "AP")
                        .param("targetHousingTypes", "OF")
                        .param("page", "0")
                        .param("size", "20")
        )
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @DisplayName("medium - 부동산 임대차 추천 데이터 조회 API 통합 테스트")
    void testGetRecommendedRealEsstateLease() throws Exception {
        String url = "/leases";

        mockMvc.perform(
                MockMvcRequestBuilders.get(baseUrl + url)
                        .param("cash", "21221")
                        .param("targetRegionIds", "14306")
                        .param("targetRegionIds", "14262")
                        .param("targetRegionIds", "14302")
                        .param("targetHousingTypes", "AP")
                        .param("targetHousingTypes", "OF")
                        .param("page", "0")
                        .param("size", "20")
        ).andExpect(status().is2xxSuccessful());

    }
}