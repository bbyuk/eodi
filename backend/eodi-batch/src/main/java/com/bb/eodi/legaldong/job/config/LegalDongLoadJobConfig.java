package com.bb.eodi.legaldong.job.config;

import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.job.dto.LegalDongItem;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
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


    /**
     * 1. 법정동 Chunk load step
     * 2. 법정동 Chunk paren mapping step
     */
    @Bean
    public Job legalDongLoad(
            Step legalDongLoadStep,
            Step legalDongParentMappingStep) {
        return new JobBuilder("legalDongLoad", jobRepository)
                .start(legalDongLoadStep)                                        // chunk 단위 load
                .next(legalDongParentMappingStep)                                // parentMapping
                .build();
    }

    /**
     * 법정동 적재 Step
     * @param legalDongItemReader                 법정동 ItemReader
     * @param legalDongLoadStepItemProcessor      법정동 ItemProcessor
     * @param legalDongLoadStepWriter             법정동 적재 Step ItemWriter
     * @return
     */
    @Bean
    public Step legalDongLoadStep(
            ItemStreamReader<LegalDongItem> legalDongItemReader,
            ItemProcessor<LegalDongItem, LegalDong> legalDongLoadStepItemProcessor,
            ItemWriter<LegalDong> legalDongLoadStepWriter
    ) {
        return new StepBuilder("legalDongLoadStep", jobRepository)
                .<LegalDongItem, LegalDong>chunk(batchProperties.batchSize(), transactionManager)
                .reader(legalDongItemReader)
                .processor(legalDongLoadStepItemProcessor)
                .writer(legalDongLoadStepWriter)
                .listener(processedDataCounter)
                .stream(legalDongItemReader)
                .build();
    }

    /**
     * 법정동 부모 매핑 Step
     * @param legalDongItemReader                         법정동 ItemReader
     * @param legalDongParentMappingStepItemProcessor     법정동 부모 매핑 Step ItemProcessor
     * @param legalDongParentMappingStepWriter            법정동 부모 매핑 Step ItemWriter
     * @return
     */
    @Bean
    public Step legalDongParentMappingStep(
            ItemStreamReader<LegalDongItem> legalDongItemReader,
            ItemProcessor<LegalDongItem, LegalDong> legalDongParentMappingStepItemProcessor,
            ItemWriter<LegalDong> legalDongParentMappingStepWriter
    ) {
        return new StepBuilder("legalDongParentMappingStep", jobRepository)
                .<LegalDongItem, LegalDong>chunk(batchProperties.batchSize(), transactionManager)
                .reader(legalDongItemReader)
                .processor(legalDongParentMappingStepItemProcessor)
                .writer(legalDongParentMappingStepWriter)
                .listener(processedDataCounter)
                .stream(legalDongItemReader)
                .build();
    }

}
