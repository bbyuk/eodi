package com.bb.eodi.deal.job.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bb.eodi.deal.job.config.DealJobContextKey.*;

/**
 * 매매 데이터 위치정보 매핑 FlowDecider
 */
@Component
public class SellPositionMappingFlowDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        ExecutionContext ctx = jobExecution.getExecutionContext();

        List<String> targets = (List<String>) ctx.get(TARGET_SELL_YEAR_MONTH.name());
        int index = ctx.getInt(TARGET_SELL_YEAR_MONTH_IDX.name());

        if (index < targets.size()) {
            ctx.putInt(TARGET_SELL_YEAR_MONTH_IDX.name(), index + 1);
            return new FlowExecutionStatus("CONTINUE");
        }

        return FlowExecutionStatus.COMPLETED;
    }
}
