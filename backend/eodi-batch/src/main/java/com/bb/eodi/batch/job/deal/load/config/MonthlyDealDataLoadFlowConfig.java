package com.bb.eodi.batch.job.deal.load.config;

import com.bb.eodi.batch.core.repository.BatchMetaRepository;
import com.bb.eodi.batch.job.deal.load.decider.FlowSkipDecider;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.bb.eodi.batch.core.enums.BatchExecutionStatus.COMPLETED;
import static com.bb.eodi.batch.core.enums.BatchExecutionStatus.CONTINUE;

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
                .on(CONTINUE.name())
                .to(apartmentSellApiFetchStep)
                .next(apartmentSellDataLoadStep)
                .from(flowSkipDecider)
                .on(COMPLETED.name())
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
                .on(CONTINUE.name())
                .to(apartmentPresaleRightSellApiFetchStep)
                .next(apartmentPresaleRightSellDataLoadStep)
                .from(flowSkipDecider)
                .on(COMPLETED.name())
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
                .on(CONTINUE.name())
                .to(multiUnitDetachedSellApiFetchStep)
                .next(multiUnitDetachedSellDataLoadStep)
                .from(skipDecider)
                .on(COMPLETED.name())
                .end()
                .build();
    }

}
