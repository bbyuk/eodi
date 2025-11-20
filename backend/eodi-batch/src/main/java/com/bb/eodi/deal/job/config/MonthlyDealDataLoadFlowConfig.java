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
        String flowName = "monthlyDealDataLoadPreprocessFlow";
        return new FlowBuilder<Flow>(flowName)
                .start(monthlyDealDataLoadPreprocessStep)
                .end();
    }

    /**
     * 아파트 매매 데이터 적재 flow
     * @param apartmentSellApiFetchStep 아파트 매매 데이터 API 요청 step
     * @param apartmentSellDataLoadStep 아파트 매매 데이터 적재 step
     * @return 아파트 매매 데이터 적재 flow
     */
    @Bean
    public Flow apartmentSellDataLoadFlow(
            Step apartmentSellApiFetchStep,
            Step apartmentSellDataLoadStep) {
        String flowName = "apartmentSellDataLoadFlow";
        FlowSkipDecider flowSkipDecider = new FlowSkipDecider(flowName, batchMetaRepository);

        return new FlowBuilder<Flow>(flowName)
                .start(flowSkipDecider)
                .on(BatchExecutionStatus.CONTINUE.name())
                .to(apartmentSellApiFetchStep)
                .next(apartmentSellDataLoadStep)
                .from(flowSkipDecider)
                .on(BatchExecutionStatus.COMPLETED.name())
                .end()
                .end();
    }

    /**
     * 아파트 분양권 매매 데이터 적재 flow
     *
     * @param apartmentPresaleRightSellApiFetchStep 아파트 분양권 매매 데이터 API 요청 step
     * @return 아파트 분양권 매매 데이터 적재 flow
     */
    @Bean
    public Flow apartmentPresaleRightSellDataLoadFlow(
            Step apartmentPresaleRightSellApiFetchStep,
            Step apartmentPresaleRightSellDataLoadStep
    ) {
        String flowName = "apartmentPresaleRightSellDataLoadFlow";
        FlowSkipDecider flowSkipDecider = new FlowSkipDecider(flowName, batchMetaRepository);
        return new FlowBuilder<Flow>(flowName)
                .start(flowSkipDecider)
                .on(BatchExecutionStatus.CONTINUE.name())
                .to(apartmentPresaleRightSellApiFetchStep)
                .next(apartmentPresaleRightSellDataLoadStep)
                .from(flowSkipDecider)
                .on(BatchExecutionStatus.COMPLETED.name())
                .end()
                .end();
    }

    /**
     * 단독/다가구주택 매매 데이터 적재 flow
     * @param multiUnitDetachedSellApiFetchStep 단독/다가구주택 매매 데이터 API 요청 step
     * @param multiUnitDetachedSellDataLoadStep 단독/다가구주택 매매 데이터 적재 step
     * @return 단독/다가구주택 매매 데이터 적재 flow
     */
    @Bean
    public Flow multiUnitDetachedSellDataLoadFlow(
            Step multiUnitDetachedSellApiFetchStep,
            Step multiUnitDetachedSellDataLoadStep
    ) {
        String flowName = "multiUnitDetachedSellDataLoadFlow";
        FlowSkipDecider skipDecider = new FlowSkipDecider(flowName, batchMetaRepository);

        return new FlowBuilder<Flow>(flowName)
                .start(skipDecider)
                .on(BatchExecutionStatus.CONTINUE.name())
                .to(multiUnitDetachedSellApiFetchStep)
                .next(multiUnitDetachedSellDataLoadStep)
                .from(skipDecider)
                .on(BatchExecutionStatus.COMPLETED.name())
                .end()
                .build();
    }


    /**
     * 연립/다세대주택 매매 데이터 적재 flow
     * @param multiHouseholdHouseSellApiFetchStep 연립 다세대주택 매매 데이터 API 요청 step
     * @param multiHouseholdHouseSellDataLoadStep 연립 다세대주택 매매 데이터 적재 step
     * @return 연립/다세대주택 매매 데이터 적재 flow
     */
    @Bean
    public Flow multiHouseholdHouseSellDataLoadFlow(
            Step multiHouseholdHouseSellApiFetchStep,
            Step multiHouseholdHouseSellDataLoadStep
    ) {
        String flowName = "multiHouseholdHouseSellDataLoadFlow";
        FlowSkipDecider skipDecider = new FlowSkipDecider(flowName, batchMetaRepository);

        return new FlowBuilder<Flow>(flowName)
                .start(skipDecider)
                    .on(BatchExecutionStatus.CONTINUE.name())
                        .to(multiHouseholdHouseSellApiFetchStep)
                        .next(multiHouseholdHouseSellDataLoadStep)
                .from(skipDecider)
                    .on(BatchExecutionStatus.COMPLETED.name())
                        .end()
                .build();
    }

    /**
     * 오피스텔 매매 실거래가 데이터 적재 배치 job flow
     * @param officetelSellApiFetchStep 오피스텔 매매 실거래가 데이터 API 요청 step
     * @param officetelSellDataLoadStep 오피스텔 매매 실거래가 데이터 적재 step
     * @return 오피스텔 매매 실거래가 데이터 적재 배치 job flow
     */
    @Bean
    public Flow officetelSellDataLoadFlow(
            Step officetelSellApiFetchStep,
            Step officetelSellDataLoadStep
    ) {
        String flowName = "officetelSellDataLoadFlow";
        FlowSkipDecider skipDecider = new FlowSkipDecider(flowName, batchMetaRepository);
        return new FlowBuilder<Flow>(flowName)
                .start(skipDecider)
                    .on(BatchExecutionStatus.CONTINUE.name())
                        .to(officetelSellApiFetchStep)
                        .next(officetelSellDataLoadStep)
                .from(skipDecider)
                    .on(BatchExecutionStatus.COMPLETED.name())
                        .end()
                .build();
    }

    /**
     * 아파트 임대차 실거래가 데이터 적재 배치 job flow
     * @param apartmentLeaseApiFetchStep 아파트 임대차 API 요청 step
     * @param apartmentLeaseDataLoadStep 아파트 임대차 데이터 적재 step
     * @return 아파트 임대차 실거래가 데이터 적재 배치 job flow
     */
    @Bean
    public Flow apartmentLeaseDataLoadFlow(
            Step apartmentLeaseApiFetchStep,
            Step apartmentLeaseDataLoadStep
    ) {
        String flowName = "apartmentLeaseDataLoadFlow";
        FlowSkipDecider skipDecider = new FlowSkipDecider(flowName, batchMetaRepository);

        return new FlowBuilder<Flow>(flowName)
                .start(skipDecider)
                    .on(BatchExecutionStatus.CONTINUE.name())
                        .to(apartmentLeaseApiFetchStep)
                        .next(apartmentLeaseDataLoadStep)
                .from(skipDecider)
                    .on(BatchExecutionStatus.COMPLETED.name())
                        .end()
                .build();
    }

    /**
     * 단독/다가구주택 전월세 실거래가 데이터 적재 배치 job flow
     * @param multiUnitDetachedLeaseApiFetchStep 단독/다가구주택 전월세 실거래가 데이터 API 요청 step
     * @param multiUnitDetachedLeaseDataLoadStep 단독/다가구주택 전월세 실거랙 데이터 적재 step
     * @return 단독/다가구주택 전월세 실거래가 데이터 적재 배치 job flow
     */
    @Bean
    public Flow multiUnitDetachedLeaseDataLoadFlow(
            Step multiUnitDetachedLeaseApiFetchStep,
            Step multiUnitDetachedLeaseDataLoadStep
    ) {
        String flowName = "multiUnitDetachedLeaseDataLoadFLow";
        FlowSkipDecider flowSkipDecider = new FlowSkipDecider(flowName, batchMetaRepository);
        return new FlowBuilder<Flow>(flowName)
                .start(flowSkipDecider)
                    .on(BatchExecutionStatus.CONTINUE.name())
                        .to(multiUnitDetachedLeaseApiFetchStep)
                        .next(multiUnitDetachedLeaseDataLoadStep)
                .from(flowSkipDecider)
                    .on(BatchExecutionStatus.COMPLETED.name())
                        .end()
                .build();
    }

    /**
     * 연립/다세대주택 전월세 실거래가 데이터 적재 flow
     * @param multiHouseholdHouseLeaseApiFetchStep 연립/다세대주택 전월세 실거래가 데이터 API 요청 step
     * @param multiHouseholdHouseLeaseDataLoadStep 연립/다세대주택 전월세 실거래가 데이터 적재 step
     * @return 연립/다세대주택 전월세 실거래가 데이터 적재 flow
     */
    @Bean
    public Flow multiHouseholdHouseLeaseDataLoadFlow(
            Step multiHouseholdHouseLeaseApiFetchStep,
            Step multiHouseholdHouseLeaseDataLoadStep
    ) {
        String flowName = "multiHouseholdHouseLeaseDataLoadFlow";
        FlowSkipDecider skipDecider = new FlowSkipDecider(flowName, batchMetaRepository);
        return new FlowBuilder<Flow>(flowName)
                .start(skipDecider)
                    .on(BatchExecutionStatus.CONTINUE.name())
                        .to(multiHouseholdHouseLeaseApiFetchStep)
                        .next(multiHouseholdHouseLeaseDataLoadStep)
                .from(skipDecider)
                    .on(BatchExecutionStatus.COMPLETED.name())
                        .end()
                .build();

    }

    /**
     * 오피스텔 전월세 실거래가 데이터 적재 flow
     * @param officetelLeaseApiFetchStep 오피스텔 전월세 실거래가 데이터 API 요청 step
     * @param officetelLeaseDataLoadStep 오피스텔 전월세 실거래가 데이터 적재 step
     * @return 오피스텔 전월세 실거래가 데이터 적재 flow
     */
    @Bean
    public Flow officetelLeaseDataLoadFlow(
            Step officetelLeaseApiFetchStep,
            Step officetelLeaseDataLoadStep
    ) {
        String flowName = "officetelLeaseDataLoadFlow";
        FlowSkipDecider skipDecider = new FlowSkipDecider(flowName, batchMetaRepository);

        return new FlowBuilder<Flow>(flowName)
                .start(skipDecider)
                    .on(BatchExecutionStatus.CONTINUE.name())
                        .to(officetelLeaseApiFetchStep)
                        .next(officetelLeaseDataLoadStep)
                .from(skipDecider)
                    .on(BatchExecutionStatus.COMPLETED.name())
                        .end()
                .build();
    }
}
