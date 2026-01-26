package com.bb.eodi.deal.job.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bb.eodi.deal.job.config.DealJobContextKey.TARGET_LEASE_YEAR_MONTH;
import static com.bb.eodi.deal.job.config.DealJobContextKey.TARGET_LEASE_YEAR_MONTH_IDX;

/**
 * 임대차 데이터 위치정보 매핑 FlowDecider
 */
@Component
public class LeasePositionMappingFlowDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        ExecutionContext ctx = jobExecution.getExecutionContext();

        List<String> targets = (List<String>) ctx.get(TARGET_LEASE_YEAR_MONTH.name());
        int index = ctx.getInt(TARGET_LEASE_YEAR_MONTH_IDX.name());

        if (index < targets.size()) {
            return new FlowExecutionStatus("CONTINUE");
        }

        return FlowExecutionStatus.COMPLETED;
    }
}