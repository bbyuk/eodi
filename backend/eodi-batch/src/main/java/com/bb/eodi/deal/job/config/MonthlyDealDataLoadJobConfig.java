package com.bb.eodi.deal.job.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * 월별 부동산 실거래가 데이터 적재 배치 job 설정
 */
@Configuration
@RequiredArgsConstructor
public class MonthlyDealDataLoadJobConfig {

    private final JobRepository jobRepository;

    /**
     * 월별 부동산 실거래가 데이터 적재 batch job
     * @return 월별 부동산 거래 데이터 적재 batch job
     */
    @Bean
    public Job monthlyDealDataLoad(
            Flow apiFetchParallelFlow,
            Flow dataLoadParallelFlow,
            JobExecutionDecider targetYearMonthDecider
    ) {

        Flow mainFlow = new FlowBuilder<SimpleFlow>("monthlyDealDataLoadMainFlow")
                .start(apiFetchParallelFlow)
                .next(dataLoadParallelFlow)
                .next(targetYearMonthDecider)

                .on("CONTINUE")
                .to(dealDataLoadPreprocessStep)

                .from(targetYearMonthDecider)
                .on(FlowExecutionStatus.COMPLETED.getName())
                .end()

                .end();


        return new JobBuilder("monthlyDealDataLoad", jobRepository)
                .start(mainFlow)
                .end()
                .build();
    }

    /**
     * API Fetch 병렬 flow
     * @param apartmentSellApiFetchFlow             아파트 매매 API
//     * @param apartmentPresaleRightSellApiFetchFlow 아파트 분양권 매매 API
//     * @param multiUnitDetachedSellApiFetchFlow     단독/다가구주택 매매 API
//     * @param multiHouseholdHouseSellApiFetchFlow   연립/다세대주택 매매 API
     * @param officetelSellApiFetchFlow             오피스텔 매매 API
     * @param apartmentLeaseApiFetchFlow            아파트 임대차 API
//     * @param multiUnitDetachedLeaseApiFetchFlow    단독/다가구주택 임대차 API
//     * @param multiHouseholdHouseLeaseApiFetchFlow  연립/다세대주택 임대차 API
     * @param officetelLeaseApiFetchFlow            오피스텔 임대차 API
     * @return API Fetch 병렬 flow
     */
    @Bean
    public Flow apiFetchParallelFlow(
            Flow apartmentSellApiFetchFlow,
//            Flow apartmentPresaleRightSellApiFetchFlow,
//            Flow multiUnitDetachedSellApiFetchFlow,
//            Flow multiHouseholdHouseSellApiFetchFlow,
            Flow officetelSellApiFetchFlow,
            Flow apartmentLeaseApiFetchFlow,
//            Flow multiUnitDetachedLeaseApiFetchFlow,
//            Flow multiHouseholdHouseLeaseApiFetchFlow,
            Flow officetelLeaseApiFetchFlow
    ) {
        SimpleAsyncTaskExecutor apiFetchExecutor = new SimpleAsyncTaskExecutor();
        apiFetchExecutor.setConcurrencyLimit(3);

        return new FlowBuilder<Flow>("apiFetchParallelFlow")
                .start(apartmentSellApiFetchFlow)
                .split(apiFetchExecutor)
                .add(
//                        apartmentPresaleRightSellApiFetchFlow,
//                        multiUnitDetachedSellApiFetchFlow,
//                        multiHouseholdHouseSellApiFetchFlow,
                        officetelSellApiFetchFlow,
                        apartmentLeaseApiFetchFlow,
//                        multiUnitDetachedLeaseApiFetchFlow,
//                        multiHouseholdHouseLeaseApiFetchFlow,
                        officetelLeaseApiFetchFlow
                ).build();
    }


    /**
     * 데이터 병렬 적재 flow
     * @param apartmentSellDataLoadFlow             아파트 매매 데이터 적재 flow
//     * @param apartmentPresaleRightSellDataLoadFlow 아파트 분양권 매매 데이터 적재 flow
//     * @param multiUnitDetachedSellDataLoadFlow     단독/다가구주택 매매 데이터 적재 flow
//     * @param multiHouseholdHouseSellDataLoadFlow   연립/다세대주택 매매 데이터 적재 flow
     * @param officetelSellDataLoadFlow             오피스텔 매매 데이터 적재 flow
     * @param apartmentLeaseDataLoadFlow            아파트 임대차 데이터 적재 flow
//     * @param multiUnitDetachedLeaseDataLoadFlow    단독/다가구주택 임대차 데이터 적재 flow
//     * @param multiHouseholdHouseLeaseDataLoadFlow  연립/다세대주택 임대차 데이터 적재 flow
     * @param officetelLeaseDataLoadFlow            오피스텔 임대차 데이터 적재 flow
     * @return 데이터 병렬 적재 flow
     */
    @Bean
    public Flow dataLoadParallelFlow(
            Flow apartmentSellDataLoadFlow,
//            Flow apartmentPresaleRightSellDataLoadFlow,
//            Flow multiUnitDetachedSellDataLoadFlow,
//            Flow multiHouseholdHouseSellDataLoadFlow,
            Flow officetelSellDataLoadFlow,
            Flow apartmentLeaseDataLoadFlow,
//            Flow multiUnitDetachedLeaseDataLoadFlow,
//            Flow multiHouseholdHouseLeaseDataLoadFlow,
            Flow officetelLeaseDataLoadFlow
    ) {
        SimpleAsyncTaskExecutor loadExecutor = new SimpleAsyncTaskExecutor();
        loadExecutor.setConcurrencyLimit(9);

        return new FlowBuilder<Flow>("dataLoadParallelFlow")
                .start(apartmentSellDataLoadFlow)
                .split(loadExecutor)
                .add(
//                        apartmentPresaleRightSellDataLoadFlow,
//                        multiUnitDetachedSellDataLoadFlow,
//                        multiHouseholdHouseSellDataLoadFlow,
                        officetelSellDataLoadFlow,
                        apartmentLeaseDataLoadFlow,
//                        multiUnitDetachedLeaseDataLoadFlow,
//                        multiHouseholdHouseLeaseDataLoadFlow,
                        officetelLeaseDataLoadFlow
                )
                .build();
    }


}
