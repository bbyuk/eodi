package com.bb.eodi.batch.job.deal.load.tasklet;

import com.bb.eodi.batch.job.deal.load.MonthlyDealDataLoadJobKey;
import com.bb.eodi.batch.job.deal.load.MonthlyDealDataLoadJobProperties;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 월별 부동산 매매 데이터 적재 배치 전처리 Step tasklet
 */
@Component
@StepScope
public class MonthlyDealDataLoadPreprocessStepTasklet implements Tasklet {

    private final String dealMonth;
    private final String tempFileRoot;

    public MonthlyDealDataLoadPreprocessStepTasklet(
            @Value("#{jobParameters['year-month']}") String dealMonth,
            @Value("#{jobParameters['temp-file-root']}") String tempFileRoot,
            MonthlyDealDataLoadJobProperties properties
    ) {
        this.dealMonth = dealMonth;
        this.tempFileRoot = tempFileRoot;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();

        jobCtx.putString(MonthlyDealDataLoadJobKey.DEAL_MONTH.name(), dealMonth);
        jobCtx.putString(MonthlyDealDataLoadJobKey.TEMP_FILE_ROOT.name(), tempFileRoot);

        return null;
    }
}
