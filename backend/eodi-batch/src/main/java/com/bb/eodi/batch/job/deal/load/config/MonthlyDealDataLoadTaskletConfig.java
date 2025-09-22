package com.bb.eodi.batch.job.deal.load.config;

import com.bb.eodi.batch.job.deal.load.tasklet.RealEstateDealApiFetchStepTasklet;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import com.bb.eodi.infrastructure.api.GovernmentDataApiProperties;
import com.bb.eodi.infrastructure.api.deal.DealDataApiClient;
import com.bb.eodi.port.out.deal.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 월별 부동산 매매데이터 적재 배치 Tasklet bean 설정
 */
@Configuration
@RequiredArgsConstructor
public class MonthlyDealDataLoadTaskletConfig {

    private final GovernmentDataApiProperties governmentDataApiProperties;
    private final LegalDongRepository legalDongRepository;
    private final DealDataApiClient dealDataApiClient;
    private final ObjectMapper objectMapper;

    /**
     * 아파트 매매 데이터 API 요청 step tasklet
     *
     * @return 아파트 매매 데이터 API 요청 step tasklet
     */
    @Bean
    @StepScope
    public Tasklet apartmentSellApiFetchStepTasklet() {
        return new RealEstateDealApiFetchStepTasklet<>(
                ApartmentSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper,
                governmentDataApiProperties.pageSize()
        );
    }

    /**
     * 아파트 분양권 매매 데이터 API 요청 step tasklet
     *
     * @return 아파트 분양권 매매 데이터 API 요청 step tasklet
     */
    @Bean
    @StepScope
    public Tasklet apartmentPresaleRightSellApiFetchStepTasklet() {
        return new RealEstateDealApiFetchStepTasklet<>(
                ApartmentPresaleRightSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper,
                governmentDataApiProperties.pageSize()
        );
    }

    /**
     * 단독/다가구주택 매매 데이터 API 요청 Step tasklet
     *
     * @return
     */
    @Bean
    @StepScope
    public Tasklet multiUnitDetachedSellApiFetchStepTasklet() {
        return new RealEstateDealApiFetchStepTasklet<>(
                MultiUnitDetachedSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper,
                governmentDataApiProperties.pageSize()
        );
    }


    /**
     * 연립/다세대주택 매매 데이터 API 요청 step tasklet
     *
     * @return 연립/다세대주택 매매 데이터 API 요청 step tasklet
     */
    @Bean
    @StepScope
    public Tasklet multiHouseholdHouseSellApiFetchStepTasklet() {
        return new RealEstateDealApiFetchStepTasklet<>(
                MultiHouseholdHouseSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper,
                governmentDataApiProperties.pageSize()
        );
    }

    /**
     * 오피스텔 매매 실거래가 데이터 API 요청 step
     *
     * @return 오피스텔 매매 실거래가 데이터 API 요청 step
     */
    @Bean
    @StepScope
    public Tasklet officetelSellApiFetchStepTasklet() {
        return new RealEstateDealApiFetchStepTasklet<>(
                OfficetelSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper,
                governmentDataApiProperties.pageSize()
        );
    }

    /**
     * 아파트 임대차 실거래가 데이터 API 요청 step tasklet
     *
     * @return 아파트 임대차 실거래가 데이터 API 요청 step tasklet
     */
    @Bean
    @StepScope
    public Tasklet apartmentLeaseApiFetchStepTasklet() {
        return new RealEstateDealApiFetchStepTasklet<>(
                ApartmentLeaseDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper,
                governmentDataApiProperties.pageSize()
        );
    }
}
