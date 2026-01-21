package com.bb.eodi.deal.job.config;

import com.bb.eodi.core.repository.BatchMetaRepository;
import com.bb.eodi.deal.job.decider.FlowSkipDecider;
import com.bb.eodi.core.enums.BatchExecutionStatus;
import com.bb.eodi.deal.job.decider.TargetYearMonthDecider;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 월별 부동산 실거래가 데이터 적재 배치 flow 설정
 */
@Configuration
@RequiredArgsConstructor
public class MonthlyDealDataLoadFlowConfig {

    private final BatchMetaRepository batchMetaRepository;

    private static final String REFERENCE_VERSION_TARGET_NAME_AP_SELL = "apartment-sell";
    private static final String REFERENCE_VERSION_TARGET_NAME_AP_PRESALE = "apartment-presale-sell";
    private static final String REFERENCE_VERSION_TARGET_NAME_MU_SELL = "multi-unit-sell";
    private static final String REFERENCE_VERSION_TARGET_NAME_MH_SELL = "multi-household-sell";
    private static final String REFERENCE_VERSION_TARGET_NAME_OF_SELL = "officetel-sell";

    private static final String REFERENCE_VERSION_TARGET_NAME_AP_LEASE = "apartment-lease";
    private static final String REFERENCE_VERSION_TARGET_NAME_MU_LEASE = "multi-unit-lease";
    private static final String REFERENCE_VERSION_TARGET_NAME_MH_LEASE = "multi-household-lease";
    private static final String REFERENCE_VERSION_TARGET_NAME_OF_LEASE = "officetel-lease";

    /**
     * 아파트 매매 데이터 적재 flow
     * @param apartmentSellDataLoadPreprocessStep 아파트 매매 데이터 적재 전처리 step
     * @param apartmentSellApiFetchStep 아파트 매매 데이터 API 요청 step
     * @param apartmentSellDataLoadStep 아파트 매매 데이터 적재 step
     * @param apartmentSellDataLoadPostprocessStep 아파트 매매 데이터 적재 후처리 step
     * @param apartmentSellTargetYearMonthDecider 아파트 매매 데이터 Flow JobExecutionDecider
     * @return 아파트 매매 데이터 적재 flow
     */
    @Bean
    public Flow apartmentSellDataLoadFlow(
            Step apartmentSellDataLoadPreprocessStep,
            Step apartmentSellApiFetchStep,
            Step apartmentSellDataLoadStep,
            Step apartmentSellDataLoadPostprocessStep,
            JobExecutionDecider apartmentSellTargetYearMonthDecider) {
        return new FlowBuilder<Flow>("apartmentSellDataLoadFlow")
                .start(apartmentSellDataLoadPreprocessStep)
                .next(apartmentSellApiFetchStep)
                .next(apartmentSellDataLoadStep)
                .next(apartmentSellDataLoadPostprocessStep)
                .next(apartmentSellTargetYearMonthDecider)
                .on("CONTINUE").to(apartmentSellDataLoadPreprocessStep)
                .from(apartmentSellTargetYearMonthDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 아파트 매매 데이터 Flow JobExecutionDecider
     * @return 아파트 매매 데이터 Flow JobExecutionDecider
     */
    @Bean
    public JobExecutionDecider apartmentSellTargetYearMonthDecider() {
        return new TargetYearMonthDecider(REFERENCE_VERSION_TARGET_NAME_AP_SELL);
    }

    /**
     * 아파트 분양권 매매 데이터 적재 flow
     * @param apartmentPresaleRightSellDataLoadPreprocessStep 아파트 분양권 매매 데이터 적재 전처리 step
     * @param apartmentPresaleRightSellApiFetchStep 아파트 분양권 매매 데이터 API 요청 step
     * @param apartmentPresaleRightSellDataLoadStep 아파트 분양권 매매 데이터 적재 step
     * @param apartmentPresaleRightSellDataLoadPostprocessStep 아파트 분양권 매매 데이터 적재 후처리 step
     * @param apartmentPresaleRightSellTargetYearMonthDecider 아파트 분양권 매매 Flow JobExecutionDecider
     * @return 아파트 분양권 매매 데이터 적재 flow
     */
    @Bean
    public Flow apartmentPresaleRightSellDataLoadFlow(
            Step apartmentPresaleRightSellDataLoadPreprocessStep,
            Step apartmentPresaleRightSellApiFetchStep,
            Step apartmentPresaleRightSellDataLoadStep,
            Step apartmentPresaleRightSellDataLoadPostprocessStep,
            JobExecutionDecider apartmentPresaleRightSellTargetYearMonthDecider) {
        String flowName = "apartmentPresaleRightSellDataLoadFlow";
        return new FlowBuilder<Flow>(flowName)
                .start(apartmentPresaleRightSellDataLoadPreprocessStep)
                .next(apartmentPresaleRightSellApiFetchStep)
                .next(apartmentPresaleRightSellDataLoadStep)
                .next(apartmentPresaleRightSellDataLoadPostprocessStep)
                .next(apartmentPresaleRightSellTargetYearMonthDecider)
                .on("CONTINUE").to(apartmentPresaleRightSellDataLoadPreprocessStep)
                .from(apartmentPresaleRightSellTargetYearMonthDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 아파트 분양권 매매 데이터 Flow JobExecutionDecider
     * @return 아파트 분양권 매매 데이터 Flow JobExecutionDecider
     */
    @Bean
    public JobExecutionDecider apartmentPresaleRightSellTargetYearMonthDecider() {
        return new TargetYearMonthDecider(REFERENCE_VERSION_TARGET_NAME_AP_PRESALE);
    }

    /**
     * 단독/다가구주택 매매 데이터 적재 flow
     * @param multiUnitDetachedSellDataLoadPreprocessStep 단독/다가구주택 매매 데이터 적재 전처리 step
     * @param multiUnitDetachedSellApiFetchStep 단독/다가구주택 매매 데이터 API 요청 step
     * @param multiUnitDetachedSellDataLoadStep 단독/다가구주택 매매 데이터 적재 step
     * @param multiUnitDetachedSellDataLoadPostprocessStep 단독/다가구주택 매매 데이터 적재 후처리 step
     * @param multiUnitDetachedSellTargetYearMonthDecider 단독/다가구주택 매매 데이터 Flow JobExecutionDecider
     * @return 단독/다가구주택 매매 데이터 적재 flow
     */
    @Bean
    public Flow multiUnitDetachedSellDataLoadFlow(
            Step multiUnitDetachedSellDataLoadPreprocessStep,
            Step multiUnitDetachedSellApiFetchStep,
            Step multiUnitDetachedSellDataLoadStep,
            Step multiUnitDetachedSellDataLoadPostprocessStep,
            JobExecutionDecider multiUnitDetachedSellTargetYearMonthDecider) {
        return new FlowBuilder<Flow>("multiUnitDetachedSellDataLoadFlow")
                .start(multiUnitDetachedSellDataLoadPreprocessStep)
                .next(multiUnitDetachedSellApiFetchStep)
                .next(multiUnitDetachedSellDataLoadStep)
                .next(multiUnitDetachedSellDataLoadPostprocessStep)
                .next(multiUnitDetachedSellTargetYearMonthDecider)
                .on("CONTINUE").to(multiUnitDetachedSellDataLoadPreprocessStep)
                .from(multiUnitDetachedSellTargetYearMonthDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 단독/다가구주택 매매 데이터 Flow JobExecutionDecider
     * @return 단독/다가구주택 매매 데이터 Flow JobExecutionDecider
     */
    @Bean
    public JobExecutionDecider multiUnitDetachedSellTargetYearMonthDecider() {
        return new TargetYearMonthDecider(REFERENCE_VERSION_TARGET_NAME_MU_SELL);
    }


    /**
     * 연립/다세대주택 매매 데이터 적재 flow
     * @param multiHouseholdHouseSellDataLoadPreprocessStep 연립/다세대주택 매매 데이터 적재 전처리 step
     * @param multiHouseholdHouseSellApiFetchStep 연립/다세대주택 매매 데이터 API 요청 step
     * @param multiHouseholdHouseSellDataLoadStep 연립/다세대주택 매매 데이터 적재 step
     * @param multiHouseholdHouseSellDataLoadPostprocessStep 연립/다세대주택 매매 데이터 적재 후처리 step
     * @param multiHouseholdHouseSellDataTargetYearMonthDecider 연립/다세대주택 매매 데이터 Flow JobExecutionDecider
     * @return 연립/다세대주택 매매 데이터 적재 flow
     */
    @Bean
    public Flow multiHouseholdHouseSellDataLoadFlow(
            Step multiHouseholdHouseSellDataLoadPreprocessStep,
            Step multiHouseholdHouseSellApiFetchStep,
            Step multiHouseholdHouseSellDataLoadStep,
            Step multiHouseholdHouseSellDataLoadPostprocessStep,
            JobExecutionDecider multiHouseholdHouseSellTargetYearMonthDecider) {
        return new FlowBuilder<Flow>("multiHouseholdHouseSellDataLoadFlow")
                .start(multiHouseholdHouseSellDataLoadPreprocessStep)
                .next(multiHouseholdHouseSellApiFetchStep)
                .next(multiHouseholdHouseSellDataLoadStep)
                .next(multiHouseholdHouseSellDataLoadPostprocessStep)
                .next(multiHouseholdHouseSellTargetYearMonthDecider)
                .on("CONTINUE").to(multiHouseholdHouseSellDataLoadPreprocessStep)
                .from(multiHouseholdHouseSellTargetYearMonthDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 연립/다세대주택 매매 데이터 Flow JobExecutionDecider
     * @return 연립/다세대주택 매매 데이터 Flow JobExecutionDecider
     */
    @Bean
    public JobExecutionDecider multiHouseholdHouseSellTargetYearMonthDecider() {
        return new TargetYearMonthDecider(REFERENCE_VERSION_TARGET_NAME_MH_SELL);
    }

    /**
     * 오피스텔 매매 실거래가 데이터 적재 배치 job flow
     * @param officetelSellDataLoadPreprocessStep 오피스텔 매매 실거래가 데이터 적재 전처리 Step
     * @param officetelSellApiFetchStep 오피스텔 매매 실거래가 데이터 API 요청 step
     * @param officetelSellDataLoadStep 오피스텔 매매 실거래가 데이터 적재 step
     * @param officetelSellDataLoadPostprocessStep 오피스텔 매매 실거래가 데이터 적재 후처리 step
     * @param officetelSellTargetYearMonthDecider 오피스텔 매매 실거래가 데이터 Flow JobExecutionDecider
     * @return 오피스텔 매매 실거래가 데이터 적재 배치 job flow
     */
    @Bean
    public Flow officetelSellDataLoadFlow(
            Step officetelSellDataLoadPreprocessStep,
            Step officetelSellApiFetchStep,
            Step officetelSellDataLoadStep,
            Step officetelSellDataLoadPostprocessStep,
            JobExecutionDecider officetelSellTargetYearMonthDecider) {
        return new FlowBuilder<Flow>("officetelSellDataLoadFlow")
                .start(officetelSellDataLoadPreprocessStep)
                .next(officetelSellApiFetchStep)
                .next(officetelSellDataLoadStep)
                .next(officetelSellDataLoadPostprocessStep)
                .next(officetelSellTargetYearMonthDecider)
                .on("CONTINUE").to(officetelSellDataLoadPreprocessStep)
                .from(officetelSellTargetYearMonthDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 오피스텔 매매 실거래가 데이터 Flow JobExecutionDecider
     * @return
     */
    @Bean
    public JobExecutionDecider officetelSellTargetYearMonthDecider() {
        return new TargetYearMonthDecider(REFERENCE_VERSION_TARGET_NAME_OF_SELL);
    }


    /**
     * 아파트 임대차 실거래가 데이터 적재 배치 job flow
     * @param apartmentLeaseDataLoadPreprocessStep 아파트 임대차 데이터 적재 전처리 step
     * @param apartmentLeaseApiFetchStep 아파트 임대차 데이터 APi 요청 step
     * @param apartmentLeaseDataLoadStep 아파트 임대차 데이터 적재 step
     * @param apartmentLeaseDataLoadPostprocessStep 아파트 임대차 데이터 적재 후처리 step
     * @param apartmentLeaseTargetYearMonthDecider 아파트 임대차 데이터 적재 Flow JobExecutionDecider
     * @return 아파트 임대차 실거래가 데이터 적재 배치 job flow
     */
    @Bean
    public Flow apartmentLeaseDataLoadFlow(
            Step apartmentLeaseDataLoadPreprocessStep,
            Step apartmentLeaseApiFetchStep,
            Step apartmentLeaseDataLoadStep,
            Step apartmentLeaseDataLoadPostprocessStep,
            JobExecutionDecider apartmentLeaseTargetYearMonthDecider) {
        return new FlowBuilder<Flow>("apartmentLeaseDataLoadFlow")
                .start(apartmentLeaseDataLoadPreprocessStep)
                .next(apartmentLeaseApiFetchStep)
                .next(apartmentLeaseDataLoadStep)
                .next(apartmentLeaseDataLoadPostprocessStep)
                .next(apartmentLeaseTargetYearMonthDecider)
                .on("CONTINUE").to(apartmentLeaseDataLoadPreprocessStep)
                .from(apartmentLeaseTargetYearMonthDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 아파트 임대차 데이터 적재 Flow JobExecutionDecider
     * @return 아파트 임대차 데이터 적재 Flow JobExecutionDecider
     */
    @Bean
    public JobExecutionDecider apartmentLeaseTargetYearMonthDecider() {
        return new TargetYearMonthDecider(REFERENCE_VERSION_TARGET_NAME_OF_LEASE);
    }

    /**
     * 단독/다가구주택 전월세 실거래가 데이터 적재 배치 job flow
     * @param multiUnitDetachedLeaseDataLoadPreprocessStep 단독/다가구주택 임대차 실거래 데이터 적재 전처리 step
     * @param multiUnitDetachedLeaseApiFetchStep 단독/다가구주택 임대차 실거래 데이터 API요청 step
     * @param multiUnitDetachedLeaseDataLoadStep 단독/다가구주택 전월세 실거랙 데이터 적재 step
     * @param multiUnitDetachedLeaseDataLoadPostprocessStep 단독/다가구주택 임대차 실거래 데이터 적재 후처리 step
     * @param multiUnitDetachedTargetYearMonthDecider 단독/다가구주택 임대차 실거래가 Flow JobExecutionDecider
     * @return 단독/다가구주택 전월세 실거래가 데이터 적재 배치 job flow
     */
    @Bean
    public Flow multiUnitDetachedLeaseDataLoadFlow(
            Step multiUnitDetachedLeaseDataLoadPreprocessStep,
            Step multiUnitDetachedLeaseApiFetchStep,
            Step multiUnitDetachedLeaseDataLoadStep,
            Step multiUnitDetachedLeaseDataLoadPostprocessStep,
            JobExecutionDecider multiUnitDetachedTargetYearMonthDecider) {
        return new FlowBuilder<Flow>("multiUnitDetachedLeaseDataLoadFlow")
                .start(multiUnitDetachedLeaseDataLoadPreprocessStep)
                .next(multiUnitDetachedLeaseApiFetchStep)
                .next(multiUnitDetachedLeaseDataLoadStep)
                .next(multiUnitDetachedLeaseDataLoadPostprocessStep)
                .next(multiUnitDetachedTargetYearMonthDecider)
                .on("CONTINUE").to(multiUnitDetachedLeaseDataLoadPreprocessStep)
                .from(multiUnitDetachedTargetYearMonthDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 단독/다가구주택 임대차 실거래가 Flow JobExecutionDecider
     * @return 단독/다가구주택 임대차 실거래가 Flow JobExecutionDecider
     */
    @Bean
    public JobExecutionDecider multiUnitDetachedTargetYearMonthDecider() {
        return new TargetYearMonthDecider(REFERENCE_VERSION_TARGET_NAME_MU_LEASE);
    }


    /**
     * 연립/다세대주택 전월세 실거래가 데이터 적재 flow
     * @param multiHouseholdHouseLeaseDataLoadPreprocessStep 연립/다세대주택 임대차 실거래가 데이터 적재 전처리 step
     * @param multiHouseholdHouseLeaseApiFetchStep 연립/다세대주택 임대차 실거래가 데이터 API 요청 step
     * @param multiHouseholdHouseLeaseDataLoadStep 연립/다세대주택 전월세 실거래가 데이터 적재 step
     * @param multiHouseholdHouseLeaseDataLoadPostprocessStep 연립/다세대주택 임대차 실거래가 데이터 적재 후처리 step
     * @param multiHouseholdHouseTargetYearMonthDecider 연립/다세대주택 임대차 실거래가 데이터 Flow JobExecutionDecider
     * @return 연립/다세대주택 전월세 실거래가 데이터 적재 flow
     */
    @Bean
    public Flow multiHouseholdHouseLeaseDataLoadFlow(
            Step multiHouseholdHouseLeaseDataLoadPreprocessStep,
            Step multiHouseholdHouseLeaseApiFetchStep,
            Step multiHouseholdHouseLeaseDataLoadStep,
            Step multiHouseholdHouseLeaseDataLoadPostprocessStep,
            JobExecutionDecider multiHouseholdHouseTargetYearMonthDecider) {
        return new FlowBuilder<Flow>("multiHouseholdHouseLeaseDataLoadFlow")
                .start(multiHouseholdHouseLeaseDataLoadPreprocessStep)
                .next(multiHouseholdHouseLeaseApiFetchStep)
                .next(multiHouseholdHouseLeaseDataLoadStep)
                .next(multiHouseholdHouseLeaseDataLoadPostprocessStep)
                .next(multiHouseholdHouseTargetYearMonthDecider)
                .on("CONTINUE").to(multiHouseholdHouseLeaseDataLoadPreprocessStep)
                .from(multiHouseholdHouseTargetYearMonthDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();

    }

    /**
     * 연립/다세대주택 임대차 실거래가 데이터 Flow JobExecutionDecider
     * @return 연립/다세대주택 임대차 실거래가 데이터 Flow JobExecutionDecider
     */
    @Bean
    public JobExecutionDecider multiHouseholdHouseTargetYearMonthDecider() {
        return new TargetYearMonthDecider(REFERENCE_VERSION_TARGET_NAME_MH_LEASE);
    }


    /**
     * 오피스텔 전월세 실거래가 데이터 적재 flow
     * @param officetelLeaseDataLoadPreprocessStep 오피스텔 임대차 실거래가 데이터 적재 전처리 step
     * @param officetelLeaseApiFetchStep 오피스텔 임대차 실거래가 데이터 API 요청 step
     * @param officetelLeaseDataLoadStep 오피스텔 전월세 실거래가 데이터 적재 step
     * @param officetelLeaseDataLoadPostprocessStep 오피스텔 임대차 실거래가 데이터 적재 후처리 step
     * @param officetelLeaseTargetYearMonthDecider 오피스텔 임대차 실거래가 데이터 Flow JobExecutionDecider
     * @return 오피스텔 전월세 실거래가 데이터 적재 flow
     */
    @Bean
    public Flow officetelLeaseDataLoadFlow(
            Step officetelLeaseDataLoadPreprocessStep,
            Step officetelLeaseApiFetchStep,
            Step officetelLeaseDataLoadStep,
            Step officetelLeaseDataLoadPostprocessStep,
            JobExecutionDecider officetelLeaseTargetYearMonthDecider) {
        return new FlowBuilder<Flow>("officetelLeaseDataLoadFlow")
                .start(officetelLeaseDataLoadPreprocessStep)
                .next(officetelLeaseApiFetchStep)
                .next(officetelLeaseDataLoadStep)
                .next(officetelLeaseDataLoadPostprocessStep)
                .next(officetelLeaseTargetYearMonthDecider)
                .on("CONTINUE").to(officetelLeaseDataLoadPreprocessStep)
                .from(officetelLeaseTargetYearMonthDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 오피스텔 임대차 실거래가 데이터 Flow JobExecutionDecider
     * @return 오피스텔 임대차 실거래가 데이터 Flow JobExecutionDecider
     */
    @Bean
    public JobExecutionDecider officetelLeaseTargetYearMonthDecider() {
        return new TargetYearMonthDecider(REFERENCE_VERSION_TARGET_NAME_OF_LEASE);
    }
}
