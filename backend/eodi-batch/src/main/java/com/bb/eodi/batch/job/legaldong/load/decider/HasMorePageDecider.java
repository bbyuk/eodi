package com.bb.eodi.batch.job.legaldong.load.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import static com.bb.eodi.batch.core.enums.BatchExecutionStatus.COMPLETED;
import static com.bb.eodi.batch.core.enums.BatchExecutionStatus.CONTINUE;
import static com.bb.eodi.batch.job.legaldong.LegalDongLoadKey.*;

/**
 * 추가로 작업할 데이터가 있는지 체크
 */
@Component
public class HasMorePageDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        ExecutionContext ctx = jobExecution.getExecutionContext();
        int totalCount = ctx.getInt(TOTAL_COUNT.name(), 0);
        int processedCount = ctx.getInt(PROCESSED_COUNT.name(), 0);


        if (processedCount < totalCount) {
            int nextPage = ctx.getInt(PAGE_NUM.name(), 0) + 1;
            ctx.putInt(PAGE_NUM.name(), nextPage);

            return new FlowExecutionStatus(CONTINUE.name());
        }

        return new FlowExecutionStatus(COMPLETED.name());
    }
}
