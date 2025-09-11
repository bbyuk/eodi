package com.bb.eodi.batch.legaldong.load;

import com.bb.eodi.batch.config.EodiBatchProperties;
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
import org.springframework.batch.item.ItemReader;
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


    /**
     * 1. 전처리 Step
     *      1.1. API 요청으로 totalCount 조회
     *      1.2. 임시 파일 생성
     *      1.3. 초기 context 변수 setting
     * 2. API 조회 및 임시 파일에 데이터 append
     *      1.2. 페이지 수만큼 API 요청
     * 3. 법정동 Chunk load step
     * 4. 법정동 Chunk paren mapping step
     * 5. 임시 파일 삭제
     */
    @Bean
    public Job legalDongLoad(
            Step legalDongLoadPreprocessStep,
            Step legalDongApiFetchStep,
            Step legalDongLoadStep,
            Step legalDongParentMappingStep,
            Step legalDongLoadPostprocessStep) {
        return new JobBuilder("legalDongLoad", jobRepository)
                .start(legalDongLoadPreprocessStep)                              // batch job 초기 context data set
                .next(legalDongApiFetchStep)                                     // 현재 Page 데이터 요청
                .next(legalDongLoadStep)                                         // chunk 단위 load
                .next(legalDongParentMappingStep)                                // parentMapping
                .next(legalDongLoadPostprocessStep)
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
            ItemReader<LegalDongApiResponseRow> legalDongLoadStepReader,
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
            ItemReader<LegalDongApiResponseRow> legalDongLoadStepReader,
            @Qualifier("legalDongParentMappingStepProcessor") ItemProcessor<LegalDongApiResponseRow, LegalDong> legalDongParentMappingStepProcessor,
            @Qualifier("legalDongParentMappingStepWriter") ItemWriter<LegalDong> legalDongParentMappingStepWriter
    ) {
        return new StepBuilder("legalDongParentMappingStep", jobRepository)
                .<LegalDongApiResponseRow, LegalDong>chunk(batchProperties.batchSize(), transactionManager)
                .reader(legalDongLoadStepReader)
                .processor(legalDongParentMappingStepProcessor)
                .writer(legalDongParentMappingStepWriter)
                .listener(processedDataCounter)
                .build();
    }

    @Bean
    public Step legalDongLoadPostprocessStep(Tasklet legalDongLoadPostprocessTasklet) {
        return new StepBuilder("legalDongLoadPostprocessStep", jobRepository)
                .tasklet(legalDongLoadPostprocessTasklet, transactionManager)
                .build();
    }

}
