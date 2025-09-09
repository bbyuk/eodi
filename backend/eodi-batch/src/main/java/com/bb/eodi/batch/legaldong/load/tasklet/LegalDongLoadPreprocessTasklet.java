package com.bb.eodi.batch.legaldong.load.tasklet;

import com.bb.eodi.batch.legaldong.load.api.LegalDongApiClient;
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

import static com.bb.eodi.batch.legaldong.LegalDongLoadKey.*;

/**
 * 법정동코드 API fetch 작업
 */
@Slf4j
@StepScope
@Component
public class LegalDongLoadPreprocessTasklet implements Tasklet {

    private final String region;
    private final LegalDongApiClient legalDongApiClient;

    public LegalDongLoadPreprocessTasklet(
            @Value("#{jobParameters['region']}") String region,
            LegalDongApiClient legalDongApiClient) {
        this.region = StringUtils.hasText(region) ? region : "";
        this.legalDongApiClient = legalDongApiClient;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        int totalCount = legalDongApiClient.getTotalCount(region);

        log.debug("{} total count : {}", region, totalCount);

        ExecutionContext ctx = contribution.getStepExecution().getJobExecution().getExecutionContext();

        // job temp file 생성
        Path tempFile = Files.createTempFile("legal-dong", ".json");
        // 2. Page File context 저장 -> reader에서 파일 read
        ctx.putString(DATA_FILE.name(), tempFile.toString());

        /**
         * 초기 데이터 set
         * pageNum은 1이 최소
         */
        ctx.putInt(READ_START_OFFSET.name(), 0);
        ctx.putInt(PAGE_SIZE.name(), 500);

        ctx.putInt(TOTAL_COUNT.name(), totalCount);
        ctx.putInt(PROCESSED_COUNT.name(), 0);
        ctx.putInt(PAGE_NUM.name(), 1);

        return RepeatStatus.FINISHED;
    }
}
