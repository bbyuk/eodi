package com.bb.eodi.batch.job.deal.load;

import com.bb.eodi.batch.core.config.EodiBatchProperties;
import com.bb.eodi.batch.job.deal.load.listener.StepSkipListener;
import com.bb.eodi.domain.deal.entity.RealEstateSell;
import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
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
            Step apartmentSaleApiFetchStep,
            Step apartmentSaleDataLoadStep
    ) {
        return new JobBuilder("monthlyDealDataLoad", jobRepository)
                .start(monthlyDealDataLoadPreprocessStep)   // 전처리
                .next(apartmentSaleApiFetchStep)            // 아파트 매매 데이터 API fetch step
                .next(apartmentSaleDataLoadStep)
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

    @Bean
    public Step apartmentSaleDataLoadStep(
            ItemReader<ApartmentSellDataItem> apartmentSellDataItemReader,
            ItemProcessor<ApartmentSellDataItem, RealEstateSell> apartmentSellDataItemProcessor,
            ItemWriter<RealEstateSell> realEstateDealItemWriter,
            StepSkipListener stepSkipListener
    ) {
        return new StepBuilder("apartmentSaleDataLoadStep", jobRepository)
                .<ApartmentSellDataItem, RealEstateSell>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(apartmentSellDataItemReader)
                .processor(apartmentSellDataItemProcessor)
                .writer(realEstateDealItemWriter)
                .listener(stepSkipListener)
                .build();
    }
}
