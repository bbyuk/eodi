package com.bb.eodi.batch.legaldong.load;

import com.bb.eodi.batch.config.EodiBatchProperties;
import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponseRow;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 법정동 코드 데이터 적재 배치 config
 */
@Configuration
@RequiredArgsConstructor
public class LegalDongLoadJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EodiBatchProperties batchProperties;
    private final StepExecutionListener processedDataCounter;

    @Bean
    public Job legalDongLoad(
            JobExecutionDecider hasMorePageDecider,
            Step legalDongLoadPreprocessStep,
            Step legalDongApiFetchStep,
            Step legalDongLoadStep,
            Step legalDongParentMappingStep) {
        return new JobBuilder("legalDongLoad", jobRepository)
                .start(legalDongLoadPreprocessStep)                        // API 메타 데이터 total size, page size 등 init
                .next(legalDongApiFetchStep)                                // 현재 Page 데이터 요청
                .next(legalDongLoadStep)                                    // chunk 단위 load
                .next(hasMorePageDecider)
                    .on("CONTINUE").to(legalDongApiFetchStep)            // CONTINUE 로직
                .from(hasMorePageDecider)
                    .on("COMPLETED").to(legalDongParentMappingStep)
                .from(hasMorePageDecider)
                    .on("FAILED").fail()
                .end()
                .build();
    }

    @Bean
    public Step legalDongLoadPreprocessStep(Tasklet legalDongLoadPreprocessTasklet) {
        return new StepBuilder("legalDongLoadPreprocessStep", jobRepository)
                .tasklet(legalDongLoadPreprocessTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step legalDongApiFetchStep(Tasklet legalDongApiFetchTasklet) {
        return new StepBuilder("legalDongApiFetchStep", jobRepository)
                .tasklet(legalDongApiFetchTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step legalDongLoadStep(
            ItemStreamReader<LegalDongApiResponseRow> legalDongLoadStepReader,
            ItemProcessor<LegalDongApiResponseRow, LegalDong> legalDongLoadStepProcessor,
            @Qualifier("legalDongLoadStepWriter") ItemWriter<LegalDong> legalDongLoadStepWriter
    ) {
        return new StepBuilder("legalDongLoadStep", jobRepository)
                .<LegalDongApiResponseRow, LegalDong>chunk(batchProperties.batchSize(), transactionManager)
                .reader(legalDongLoadStepReader)
                .processor(legalDongLoadStepProcessor)
                .writer(legalDongLoadStepWriter)
                .listener(processedDataCounter)
                .build();
    }

    @Bean
    public Step legalDongParentMappingStep(
            ItemStreamReader<LegalDongApiResponseRow> legalDongLoadStepReader,
            ItemProcessor<LegalDongApiResponseRow, LegalDong> legalDongLoadStepProcessor,
            @Qualifier("legalDongParentMappingStepWriter") ItemWriter<LegalDong> legalDongParentMappingStepWriter
    ) {
        return new StepBuilder("legalDongParentMappingStep", jobRepository)
                .<LegalDongApiResponseRow, LegalDong>chunk(batchProperties.batchSize(), transactionManager)
                .reader(legalDongLoadStepReader)
                .processor(legalDongLoadStepProcessor)
                .writer(legalDongParentMappingStepWriter)
                .listener(processedDataCounter)
                .build();
    }


}
