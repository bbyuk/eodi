package com.bb.eodi.batch.job.deal.load.config;

import com.bb.eodi.batch.job.deal.load.tasklet.RealEstateDealApiFetchStepTasklet;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import com.bb.eodi.infrastructure.api.deal.DealDataApiClient;
import com.bb.eodi.port.out.deal.dto.ApartmentPresaleRightSellDataItem;
import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
import com.bb.eodi.port.out.deal.dto.MultiHouseholdHouseSellDataItem;
import com.bb.eodi.port.out.deal.dto.MultiUnitDetachedSellDataItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 월별 부동산 매매데이터 적재 배치 Tasklet bean 설정
 */
@Configuration
public class MonthlyDealDataLoadTaskletConfig {
    /**
     * 아파트 매매 데이터 API 요청 step tasklet
     *
     * @param legalDongRepository 법정동 repository
     * @param dealDataApiClient   거래 데이터 API client
     * @param objectMapper        object mapper
     * @return 아파트 매매 데이터 API 요청 step tasklet
     */
    @Bean
    @StepScope
    public Tasklet apartmentSellApiFetchStepTasklet(LegalDongRepository legalDongRepository,
                                                    DealDataApiClient dealDataApiClient,
                                                    ObjectMapper objectMapper) {
        return new RealEstateDealApiFetchStepTasklet<>(
                ApartmentSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper
        );
    }

    /**
     * 아파트 분양권 매매 데이터 API 요청 step tasklet
     *
     * @param legalDongRepository 법정동 repository
     * @param dealDataApiClient   부동산 거래 데이터 API Client
     * @param objectMapper        objectMapper
     * @return 아파트 분양권 매매 데이터 API 요청 step tasklet
     */
    @Bean
    @StepScope
    public Tasklet apartmentPresaleRightSellApiFetchStepTasklet(
            LegalDongRepository legalDongRepository,
            DealDataApiClient dealDataApiClient,
            ObjectMapper objectMapper
    ) {
        return new RealEstateDealApiFetchStepTasklet<>(
                ApartmentPresaleRightSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper
        );
    }

    /**
     * 단독/다가구주택 매매 데이터 API 요청 Step tasklet
     * @param legalDongRepository 법정동 repository
     * @param dealDataApiClient   부동산 거래 데이터 API Client
     * @param objectMapper        objectMapper
     * @return
     */
    @Bean
    @StepScope
    public Tasklet multiUnitDetachedSellApiFetchStepTasklet(
            LegalDongRepository legalDongRepository,
            DealDataApiClient dealDataApiClient,
            ObjectMapper objectMapper
    ) {
        return new RealEstateDealApiFetchStepTasklet<>(
                MultiUnitDetachedSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper
        );
    }


    /**
     * 연립/다세대주택 매매 데이터 API 요청 step tasklet
     * @param legalDongRepository 법정동 repository
     * @param dealDataApiClient   부동산 거래 데이터 API Client
     * @param objectMapper        objectMapper
     * @return 연립/다세대주택 매매 데이터 API 요청 step tasklet
     */
    @Bean
    @StepScope
    public Tasklet multiHouseholdHouseSellApiFetchStepTasklet(
            LegalDongRepository legalDongRepository,
            DealDataApiClient dealDataApiClient,
            ObjectMapper objectMapper
    ) {
        return new RealEstateDealApiFetchStepTasklet<>(
                MultiHouseholdHouseSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper
        );
    }
}
