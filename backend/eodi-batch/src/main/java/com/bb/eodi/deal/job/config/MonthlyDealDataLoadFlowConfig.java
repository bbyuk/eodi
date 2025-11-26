package com.bb.eodi.deal.job.config;

import com.bb.eodi.core.repository.BatchMetaRepository;
import com.bb.eodi.deal.job.decider.FlowSkipDecider;
import com.bb.eodi.core.enums.BatchExecutionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 월별 부동산 실거래가 데이터 적재 배치 flow 설정
 */
@Configuration
@RequiredArgsConstructor
public class MonthlyDealDataLoadFlowConfig {

    private final BatchMetaRepository batchMetaRepository;

    /**
     * 월별 부동산 거래 데이터 적재 배치 전처리 flow
     *
     * @param monthlyDealDataLoadPreprocessStep 월별 부동산 거래 데이터 적재 배치 전처리 step
     * @return 월별 부동산 거래 데이터 적재 배치 전처리 flow
     */
    @Bean
    public Flow monthlyDealDataLoadPreprocessFlow(Step monthlyDealDataLoadPreprocessStep) {
        return new FlowBuilder<Flow>("monthlyDealDataLoadPreprocessFlow")
                .start(monthlyDealDataLoadPreprocessStep)
                .end();
    }

    /**
     * 아파트 매매 데이터 API 요청 flow
     * @param apartmentSellApiFetchStep 아파트 매매 데이터 API 요청 step
     * @return 아파트 매매 데이터 API 요청 flow
     */
    @Bean
    public Flow apartmentSellApiFetchFlow(Step apartmentSellApiFetchStep) {
        return new FlowBuilder<Flow>("apartmentSellApiFetchFlow")
                .start(apartmentSellApiFetchStep)
                .build();
    }

    /**
     * 아파트 매매 데이터 적재 flow
     * @param apartmentSellDataLoadStep 아파트 매매 데이터 적재 step
     * @return 아파트 매매 데이터 적재 flow
     */
    @Bean
    public Flow apartmentSellDataLoadFlow(Step apartmentSellDataLoadStep) {
        return new FlowBuilder<Flow>("apartmentSellDataLoadFlow")
                .start(apartmentSellDataLoadStep)
                .build();
    }

    /**
     * 아파트 분양권 매매데이터 API 요청 flow
     * @param apartmentPresaleRightSellApiFetchStep 아파트 분양권 매매데이터 API 요청 step
     * @return 아파트 분양권 매매데이터 API 요청 flow
     */
    @Bean
    public Flow apartmentPresaleRightSellApiFetchFlow(Step apartmentPresaleRightSellApiFetchStep) {
        return new FlowBuilder<Flow>("apartmentPresaleRightSellApiFetchFlow")
                .start(apartmentPresaleRightSellApiFetchStep)
                .build();
    }

    /**
     * 아파트 분양권 매매 데이터 적재 flow
     *
     * @param apartmentPresaleRightSellDataLoadStep 아파트 분양권 매매 데이터 적재 step
     * @return 아파트 분양권 매매 데이터 적재 flow
     */
    @Bean
    public Flow apartmentPresaleRightSellDataLoadFlow(Step apartmentPresaleRightSellDataLoadStep) {
        String flowName = "apartmentPresaleRightSellDataLoadFlow";
        return new FlowBuilder<Flow>(flowName)
                .start(apartmentPresaleRightSellDataLoadStep)
                .build();
    }

    /**
     * 단독/다가구주택 매매데이터 API 요청 flow
     * @param multiUnitDetachedSellApiFetchStep 단독/다가구주택 매매데이터 API요청 Step
     * @return 단독/다가구주택 매매데이터 API 요청 flow
     */
    @Bean
    public Flow multiUnitDetachedSellApiFetchFlow(Step multiUnitDetachedSellApiFetchStep) {
        return new FlowBuilder<Flow>("multiUnitDetachedSellApiFetchFlow")
                .start(multiUnitDetachedSellApiFetchStep)
                .build();
    }

    /**
     * 단독/다가구주택 매매 데이터 적재 flow
     * @param multiUnitDetachedSellDataLoadStep 단독/다가구주택 매매 데이터 적재 step
     * @return 단독/다가구주택 매매 데이터 적재 flow
     */
    @Bean
    public Flow multiUnitDetachedSellDataLoadFlow(Step multiUnitDetachedSellDataLoadStep) {
        return new FlowBuilder<Flow>("multiUnitDetachedSellDataLoadFlow")
                .start(multiUnitDetachedSellDataLoadStep)
                .build();
    }

    /**
     * 연립/다세대주택 매매데이터 API 요청 flow
     * @param multiHouseholdHouseSellApiFetchStep 연립/다세대주택 매매데이터 API 요청 step
     * @return 연립/다세대주택 매매데이터 API 요청 flow
     */
    @Bean
    public Flow multiHouseholdHouseSellApiFetchFlow(Step multiHouseholdHouseSellApiFetchStep) {
        return new FlowBuilder<Flow>("multiHouseholdHouseSellApiFetchFlow")
                .start(multiHouseholdHouseSellApiFetchStep)
                .build();
    }


    /**
     * 연립/다세대주택 매매 데이터 적재 flow
     * @param multiHouseholdHouseSellDataLoadStep 연립 다세대주택 매매 데이터 적재 step
     * @return 연립/다세대주택 매매 데이터 적재 flow
     */
    @Bean
    public Flow multiHouseholdHouseSellDataLoadFlow(Step multiHouseholdHouseSellDataLoadStep) {
        return new FlowBuilder<Flow>("multiHouseholdHouseSellDataLoadFlow")
                .start(multiHouseholdHouseSellDataLoadStep)
                .build();
    }

    /**
     * 오피스텔 매매 실거래가 데이터 API 요청 flow
     * @param officetelSellApiFetchStep 오피스텔 매매 실거래가 데이터 API 요청 step
     * @return 오피스텔 매매 실거래가 데이터 API 요청 flow
     */
    @Bean
    public Flow officetelSellApiFetchFlow(Step officetelSellApiFetchStep) {
        return new FlowBuilder<Flow>("officetelSellApiFetchFlow")
                .start(officetelSellApiFetchStep)
                .build();
    }

    /**
     * 오피스텔 매매 실거래가 데이터 적재 배치 job flow
     * @param officetelSellDataLoadStep 오피스텔 매매 실거래가 데이터 적재 step
     * @return 오피스텔 매매 실거래가 데이터 적재 배치 job flow
     */
    @Bean
    public Flow officetelSellDataLoadFlow(Step officetelSellDataLoadStep) {
        return new FlowBuilder<Flow>("officetelSellDataLoadFlow")
                .start(officetelSellDataLoadStep)
                .build();
    }

    /**
     * 아파트 임대차 실거래가 데이터 API 요청 flow
     * @param apartmentLeaseApiFetchStep 아파트 임대차 실거래가 데이터 API 요청 step
     * @return 아파트 임대차 실거래가 데이터 API 요청 flow
     */
    @Bean
    public Flow apartmentLeaseApiFetchFlow(Step apartmentLeaseApiFetchStep) {
        return new FlowBuilder<Flow>("apartmentLeaseApiFetchFlow")
                .start(apartmentLeaseApiFetchStep)
                .build();
    }

    /**
     * 아파트 임대차 실거래가 데이터 적재 배치 job flow
     * @param apartmentLeaseDataLoadStep 아파트 임대차 데이터 적재 step
     * @return 아파트 임대차 실거래가 데이터 적재 배치 job flow
     */
    @Bean
    public Flow apartmentLeaseDataLoadFlow(Step apartmentLeaseDataLoadStep) {
        return new FlowBuilder<Flow>("apartmentLeaseDataLoadFlow")
                .start(apartmentLeaseDataLoadStep)
                .build();
    }


    /**
     * 단독/다가구주택 임대차 실거래가 데이터 API 요청 flow
     * @param multiUnitDetachedLeaseApiFetchStep 단독/다가구주택 임대차 실거래가 데이터 API 요청 step
     * @return 단독/다가구주택 임대차 실거래가 데이터 API 요청 flow
     */
    @Bean
    public Flow multiUnitDetachedLeaseApiFetchFlow(Step multiUnitDetachedLeaseApiFetchStep) {
        return new FlowBuilder<Flow>("multiUnitDetachedLeaseApiFetchFlow")
                .start(multiUnitDetachedLeaseApiFetchStep)
                .build();
    }

    /**
     * 단독/다가구주택 전월세 실거래가 데이터 적재 배치 job flow
     * @param multiUnitDetachedLeaseDataLoadStep 단독/다가구주택 전월세 실거랙 데이터 적재 step
     * @return 단독/다가구주택 전월세 실거래가 데이터 적재 배치 job flow
     */
    @Bean
    public Flow multiUnitDetachedLeaseDataLoadFlow(Step multiUnitDetachedLeaseDataLoadStep) {
        return new FlowBuilder<Flow>("multiUnitDetachedLeaseDataLoadFlow")
                .start(multiUnitDetachedLeaseDataLoadStep)
                .build();
    }

    /**
     * 연립/다세대주택 임대차 실거래가 데이터 API 요청 flow
     * @param multiHouseholdHouseLeaseApiFetchStep 연립/다세대주택 임대차 실거래가 데이터 API 요청 step
     * @return 연립/다세대주택 임대차 실거래가 데이터 API 요청 flow
     */
    @Bean
    public Flow multiHouseholdHouseLeaseApiFetchFlow(Step multiHouseholdHouseLeaseApiFetchStep) {
        return new FlowBuilder<Flow>("multiHouseholdHouseLeaseApiFetchFlow")
                .start(multiHouseholdHouseLeaseApiFetchStep)
                .build();
    }

    /**
     * 연립/다세대주택 전월세 실거래가 데이터 적재 flow
     * @param multiHouseholdHouseLeaseDataLoadStep 연립/다세대주택 전월세 실거래가 데이터 적재 step
     * @return 연립/다세대주택 전월세 실거래가 데이터 적재 flow
     */
    @Bean
    public Flow multiHouseholdHouseLeaseDataLoadFlow(Step multiHouseholdHouseLeaseDataLoadStep) {
        return new FlowBuilder<Flow>("multiHouseholdHouseLeaseDataLoadFlow")
                .start(multiHouseholdHouseLeaseDataLoadStep)
                .build();

    }

    /**
     * 오피스텔 임대차 실거래가 데이터 API 요청 flow
     * @param officetelLeaseApiFetchStep 오피스텔 임대차 실거래가 데이터 API 요청 step
     * @return 오피스텔 임대차 실거래가 데이터 API 요청 flow
     */
    @Bean
    public Flow officetelLeaseApiFetchFlow(Step officetelLeaseApiFetchStep) {
        return new FlowBuilder<Flow>("officetelLeaseApiFetchFlow")
                .start(officetelLeaseApiFetchStep)
                .build();
    }

    /**
     * 오피스텔 전월세 실거래가 데이터 적재 flow
     * @param officetelLeaseDataLoadStep 오피스텔 전월세 실거래가 데이터 적재 step
     * @return 오피스텔 전월세 실거래가 데이터 적재 flow
     */
    @Bean
    public Flow officetelLeaseDataLoadFlow(Step officetelLeaseDataLoadStep) {
        return new FlowBuilder<Flow>("officetelLeaseDataLoadFlow")
                .start(officetelLeaseDataLoadStep)
                .build();
    }
}
