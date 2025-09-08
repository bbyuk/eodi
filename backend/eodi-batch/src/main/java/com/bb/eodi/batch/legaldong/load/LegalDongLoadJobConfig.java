package com.bb.eodi.batch.legaldong.load;

import com.bb.eodi.batch.legaldong.load.model.LegalDongRow;
import com.bb.eodi.batch.legaldong.load.processor.LegalDongRowProcessor;
import com.bb.eodi.batch.legaldong.load.reader.LegalDongRowReader;
import com.bb.eodi.batch.legaldong.load.tasklet.LegalDongApiFetchTasklet;
import com.bb.eodi.batch.legaldong.load.writer.LegalDongRowWriter;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 법정동 코드 데이터 적재 배치 config
 */
@Configuration
@RequiredArgsConstructor
public class LegalDongLoadJobConfig {

    private static final int CHUNK_SIZE = 1000;

    private final LegalDongRowReader legalDongRowReader;
    private final LegalDongRowProcessor legalDongRowProcessor;
    private final LegalDongRowWriter legalDongRowWriter;
    private final LegalDongApiFetchTasklet legalDongApiFetchTasklet;

    @Bean
    public Job legalDongLoad(JobRepository jobRepository, Step legalDongApiFetchStep, Step legalDongLoadStep) {
        return new JobBuilder("legalDongLoad", jobRepository)
                .start(legalDongApiFetchStep)
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
                .<LegalDongRow, LegalDong> chunk(CHUNK_SIZE, transactionManager)
                .reader(legalDongRowReader)
                .processor(legalDongRowProcessor)
                .writer(legalDongRowWriter)
                .build();
    }


}
