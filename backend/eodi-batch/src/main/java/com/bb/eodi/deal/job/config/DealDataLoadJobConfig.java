package com.bb.eodi.deal.job.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
     *
     * @param dealDataLoadJobPreprocessStep 부동산 실거래가 데이터 적재 Job 전처리 Step
     * @param dealDataLoadParallelFlow      부동산 실거래가 데이터 병렬 적재 flow
     * @param dealDataPositionMappingFlow   부동산 실거래가 데이터 위치정보 매핑 flow
     * @return 부동산 거래 데이터 적재 batch job
     */
    @Bean
    public Job dealDataLoad(
            Step dealDataLoadJobPreprocessStep,
            Flow dealDataLoadParallelFlow,
            Flow dealDataPositionMappingFlow
    ) {

        SimpleFlow mainFlow = new FlowBuilder<SimpleFlow>("dealDataLoadMainFlow")
                .start(dealDataLoadJobPreprocessStep)
                .next(dealDataLoadParallelFlow)
                .next(dealDataPositionMappingFlow)
                .build();

        return new JobBuilder("dealDataLoadJob", jobRepository)
                .start(mainFlow)
                .end()
                .build();
    }

}
