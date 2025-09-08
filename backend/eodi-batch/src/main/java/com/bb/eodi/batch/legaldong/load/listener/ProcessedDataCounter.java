package com.bb.eodi.batch.legaldong.load.listener;

import com.bb.eodi.batch.legaldong.LegalDongLoadKey;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

/**
 * Chunk 처리 step 이후 처리 데이터 카운터
 */
@Component
@RequiredArgsConstructor
public class ProcessedDataCounter implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExecutionContext ctx = stepExecution.getJobExecution().getExecutionContext();

//        int processedCount = ctx.getInt(LegalDongLoadKey.PROCESSED_COUNT.name(), 0) + (int) stepExecution.getWriteCount();

        // TODO 임시로 write 없이 chunk size만큼 processcount 집계
        int processedCount = ctx.getInt(LegalDongLoadKey.PROCESSED_COUNT.name(), 0) + 1000;

        ctx.putInt(LegalDongLoadKey.PROCESSED_COUNT.name(), processedCount);
        return StepExecutionListener.super.afterStep(stepExecution);
    }
}
