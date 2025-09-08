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

/**
 * 법정동코드 API fetch 작업
 */
@Slf4j
@StepScope
@Component
public class LegalDongApiInitialFetchTasklet implements Tasklet {

    private final String region;
    private final LegalDongApiClient legalDongApiClient;

    public LegalDongApiInitialFetchTasklet(
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
        ctx.putInt("totalCount", totalCount);

        return RepeatStatus.FINISHED;
    }
}
