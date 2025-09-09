package com.bb.eodi.batch.legaldong.load;

import com.bb.eodi.batch.config.EodiBatchProperties;
import com.bb.eodi.batch.legaldong.load.decider.HasMorePageDecider;
import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponseRow;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
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

    private final EodiBatchProperties batchProperties;
    private final ItemStreamReader<LegalDongApiResponseRow> legalDongRowReader;
    private final ItemProcessor<LegalDongApiResponseRow, LegalDong> legalDongRowProcessor;
    private final ItemWriter<LegalDong> legalDongRowWriter;
    private final Tasklet legalDongLoadPreprocessTasklet;
    private final Tasklet legalDongApiFetchTasklet;
    private final StepExecutionListener processedDataCounter;

    @Bean
    public Job legalDongLoad(JobRepository jobRepository,
                             HasMorePageDecider decider,
                             Step legalDongLoadPreprocessStep,
                             Step legalDongApiFetchStep,
                             Step legalDongLoadStep) {
        return new JobBuilder("legalDongLoad", jobRepository)
                .start(legalDongLoadPreprocessStep)                        // API 메타 데이터 total size, page size 등 init
                .next(legalDongApiFetchStep)                                // 현재 Page 데이터 요청
                .next(legalDongLoadStep)                                    // chunk 단위 load
                .next(decider)
                    .on("CONTINUE").to(legalDongApiFetchStep)            // CONTINUE 로직
                .from(decider)
                    .on("*").end()                                   // FINISHED/STOP 로직
                .end()
                .build();
    }

    @Bean
    public Step legalDongLoadPreprocessStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("legalDongLoadPreprocessStep", jobRepository)
                .tasklet(legalDongLoadPreprocessTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step legalDongApiFetchStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("legalDongApiFetchStep", jobRepository)
                .tasklet(legalDongApiFetchTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step legalDongLoadStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("legalDongLoadStep", jobRepository)
                .<LegalDongApiResponseRow, LegalDong>chunk(batchProperties.batchSize(), transactionManager)
                .reader(legalDongRowReader)
                .processor(legalDongRowProcessor)
                .writer(legalDongRowWriter)
                .listener(processedDataCounter)
                .build();
    }


}
