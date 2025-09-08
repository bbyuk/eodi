package com.bb.eodi.batch.legaldong.load.tasklet;

import com.bb.eodi.batch.legaldong.LegalDongLoadKey;
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

import static com.bb.eodi.batch.legaldong.LegalDongLoadKey.*;

/**
 * 법정동 코드 API 요청 Tasklet
 */
@Slf4j
@StepScope
@Component
public class LegalDongApiFetchTasklet implements Tasklet {

    private final LegalDongApiClient legalDongApiClient;
    private final String targetRegion;

    public LegalDongApiFetchTasklet(LegalDongApiClient legalDongApiClient,
                                    @Value("#{jobParameters['region']}") String region) {
        this.legalDongApiClient = legalDongApiClient;
        this.targetRegion = region;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext ctx = contribution.getStepExecution().getJobExecution().getExecutionContext();
        String responseBody = legalDongApiClient.findByRegion(targetRegion);




        return RepeatStatus.FINISHED;
    }
}
