package com.bb.eodi.batch.deal.decider;

import com.bb.eodi.batch.core.enums.BatchExecutionStatus;
import com.bb.eodi.batch.core.repository.BatchMetaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * 월별 데이터 적재 flow 스킵 여부를 결정하는 decider
 */
@Slf4j
@RequiredArgsConstructor
public class FlowSkipDecider implements JobExecutionDecider {

    private final String targetFlowName;
    private final BatchMetaRepository batchMetaRepository;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String stepName = targetFlowName.replace("DataLoadFlow", "DataLoadStep");
        String jobName = jobExecution.getJobInstance().getJobName();
        JobParameters params = jobExecution.getJobParameters();

        // 조건 체크
        String targetYearMonth = params.getString("year-month");
        if (batchMetaRepository.isCompletedMonthlyStep(jobName, stepName, targetYearMonth)) {
            log.info("flow skip ::: step:{}, targetYearMonth:{}", stepName, jobName);
            return new FlowExecutionStatus(BatchExecutionStatus.COMPLETED.name());
        }

        return new FlowExecutionStatus(BatchExecutionStatus.CONTINUE.name());
    }
}
