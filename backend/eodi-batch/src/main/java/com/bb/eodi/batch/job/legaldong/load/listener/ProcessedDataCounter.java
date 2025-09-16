package com.bb.eodi.batch.job.legaldong.load.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import static com.bb.eodi.batch.job.legaldong.LegalDongLoadKey.*;

/**
 * Chunk 처리 step 이후 처리 데이터 카운터
 */
@Component
@RequiredArgsConstructor
public class ProcessedDataCounter implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext ctx = stepExecution.getExecutionContext();
        ctx.putInt(PROCESSED_COUNT.name(), 0);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExecutionContext ctx = stepExecution.getExecutionContext();
        int processedCount = ctx.getInt(PROCESSED_COUNT.name(), 0)
                        + (int) stepExecution.getWriteCount();
        ctx.putInt(PROCESSED_COUNT.name(), processedCount);

        return stepExecution.getExitStatus();
    }
}
