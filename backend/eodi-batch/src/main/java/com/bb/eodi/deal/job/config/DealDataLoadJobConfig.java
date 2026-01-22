package com.bb.eodi.deal.job.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
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
public class DealDataLoadJobConfig {

    private final JobRepository jobRepository;

    /**
     * 부동산 실거래가 데이터 적재 batch job
     * @return 부동산 거래 데이터 적재 batch job
     */
    @Bean
    public Job dealDataLoad(
            Flow apartmentSellDataLoadFlow,
            Flow apartmentPresaleRightSellDataLoadFlow,
            Flow multiUnitDetachedSellDataLoadFlow,
            Flow multiHouseholdHouseSellDataLoadFlow,
            Flow officetelSellDataLoadFlow,
            Flow apartmentLeaseDataLoadFlow,
            Flow multiUnitDetachedLeaseDataLoadFlow,
            Flow multiHouseholdHouseLeaseDataLoadFlow,
            Flow officetelLeaseDataLoadFlow
    ) {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(3);


        Flow mainFlow = new FlowBuilder<SimpleFlow>("dealDataLoadParallelFlow")
                .start(apartmentSellDataLoadFlow)
                .split(executor)
                .add(
                        officetelSellDataLoadFlow,
                        apartmentLeaseDataLoadFlow,
                        officetelLeaseDataLoadFlow
                )
                .build();


        return new JobBuilder("dealDataLoadJob", jobRepository)
                .start(mainFlow)
                .end()
                .build();
    }
}
