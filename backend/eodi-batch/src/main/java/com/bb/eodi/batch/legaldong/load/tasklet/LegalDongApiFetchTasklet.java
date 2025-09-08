package com.bb.eodi.batch.legaldong.load.tasklet;

import com.bb.eodi.batch.legaldong.load.api.LegalDongApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * 법정동 코드 API 요청 Tasklet
 */
@Slf4j
@StepScope
@Component
public class LegalDongApiFetchTasklet implements Tasklet {

    private final LegalDongApiClient legalDongApiClient;

    public LegalDongApiFetchTasklet(LegalDongApiClient legalDongApiClient) {
        this.legalDongApiClient = legalDongApiClient;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext ctx = contribution.getStepExecution().getJobExecution().getExecutionContext();


        return RepeatStatus.FINISHED;
    }
}
