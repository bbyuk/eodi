package com.bb.eodi.deal.job.listener;

import com.bb.eodi.deal.job.config.DealJobContextKey;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;

/**
 * 부동산 매매 실거래가 데이터 매핑 Step 종료 후 JobExecutionContext index 추가 listener
 */
public class SellPositionMappingStepExecutionListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        ExecutionContext jobCtx = stepExecution.getJobExecution().getExecutionContext();

        int index = jobCtx.getInt(DealJobContextKey.TARGET_SELL_YEAR_MONTH_IDX.name());
        jobCtx.put(DealJobContextKey.TARGET_SELL_YEAR_MONTH_IDX.name(), index + 1);

        return ExitStatus.COMPLETED;
    }
}
