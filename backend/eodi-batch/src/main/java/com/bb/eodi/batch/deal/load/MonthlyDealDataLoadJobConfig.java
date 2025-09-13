package com.bb.eodi.batch.deal.load;

import com.bb.eodi.batch.config.EodiBatchProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 월별 부동산 거래 데이터 적재 배치 job 설정
 */
@Configuration
@RequiredArgsConstructor
public class MonthlyDealDataLoadJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final EodiBatchProperties eodiBatchProperties;

    @Bean
    public Job monthlyDealDataLoad(
            Step monthlyDealDataLoadPreprocessStep,
            Step apartmentSaleApiFetchStep
    ) {
        return new JobBuilder("monthlyDealDataLoad", jobRepository)
                .start(monthlyDealDataLoadPreprocessStep)   // 전처리
                .next(apartmentSaleApiFetchStep)            // 아파트 매매 데이터 API fetch step
                .build();
    }

    @Bean
    public Step monthlyDealDataLoadPreprocessStep(Tasklet monthlyDealDataLoadPreprocessStepTasklet) {
        return new StepBuilder("monthlyDealDataLoadPreprocessStep", jobRepository)
                .tasklet(monthlyDealDataLoadPreprocessStepTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step apartmentSaleApiFetchStep(Tasklet apartmentSaleApiFetchStepTasklet) {
        return new StepBuilder("apartmentSaleApiFetchStep", jobRepository)
                .tasklet(apartmentSaleApiFetchStepTasklet, transactionManager)
                .build();
    }
}
