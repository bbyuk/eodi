package com.bb.eodi.batch.legaldong.load.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

/**
 * 추가로 작업할 데이터가 있는지 체크
 */
@Component
public class HasMorePageDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        ExecutionContext ctx = jobExecution.getExecutionContext();
        int totalCount = ctx.getInt("totalCount", 0);
        int processedCount = ctx.getInt("processedCount", 0);


        if (processedCount < totalCount) {
            int nextPage = ctx.getInt("pageNum", 0) + 1;
            ctx.putInt("pageNum", nextPage);

            return new FlowExecutionStatus("CONTINUE");
        }

        return FlowExecutionStatus.COMPLETED;
    }
}
