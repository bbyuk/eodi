package com.bb.eodi.address.job.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TargetDateDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(
            JobExecution jobExecution,
            StepExecution stepExecution
    ) {
        ExecutionContext ctx = jobExecution.getExecutionContext();

        LocalDate targetDate = (LocalDate) ctx.get("targetDate");
        LocalDate toDate     = (LocalDate) ctx.get("toDate");

        if (targetDate.isBefore(toDate)) {
            return new FlowExecutionStatus("CONTINUE");
        }
        return FlowExecutionStatus.COMPLETED;
    }
}