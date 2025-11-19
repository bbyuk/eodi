package com.bb.eodi.batch.job.legaldong.tasklet;

import com.bb.eodi.batch.job.legaldong.LegalDongLoadBatchJobProperties;
import com.bb.eodi.batch.job.legaldong.api.LegalDongApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;

import static com.bb.eodi.batch.job.legaldong.enums.LegalDongLoadKey.*;

/**
 * 법정동코드 API fetch 작업
 */
@Slf4j
@StepScope
@Component
public class LegalDongLoadPreprocessTasklet implements Tasklet {

    private final String region;
    private final LegalDongApiClient legalDongApiClient;
    private final LegalDongLoadBatchJobProperties batchJobProperties;

    public LegalDongLoadPreprocessTasklet(
            @Value("#{jobParameters['region']}") String region,
            LegalDongApiClient legalDongApiClient,
            LegalDongLoadBatchJobProperties batchJobProperties) {
        this.region = StringUtils.hasText(region) ? region : "";
        this.legalDongApiClient = legalDongApiClient;
        this.batchJobProperties = batchJobProperties;
    }

    /**
     * 1. API 요청으로 대상 지역 total count 조회
     * 2. 임시 파일 생성
     * 3. 초기 context 변수 setting
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext ctx = contribution.getStepExecution().getJobExecution().getExecutionContext();

        // total count 조회
        int totalCount = legalDongApiClient.getTotalCount(region);
        log.debug("{} total count : {}", region, totalCount);

        // job temp file 생성
        Path tempFile = Files.createTempFile(batchJobProperties.tempFileName(), batchJobProperties.tempFileSuffix());

        /**
         * 초기 context 변수 setting
         */
        ctx.putInt(READ_START_OFFSET.name(), 0);
        ctx.putString(DATA_FILE.name(), tempFile.toString());
        ctx.putInt(TOTAL_COUNT.name(), totalCount);

        return RepeatStatus.FINISHED;
    }
}
