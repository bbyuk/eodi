package com.bb.eodi.batch.deal.load.listener;

import com.bb.eodi.common.batch.repository.BatchMetaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * 월별 데이터 적재 Step Skip listener
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StepSkipListener implements StepExecutionListener {

    private final BatchMetaRepository batchMetaRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        String stepName = stepExecution.getStepName();
        String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
        JobParameters params = stepExecution.getJobExecution().getJobParameters();

        // 조건 체크
        String targetYearMonth = params.getString("year-month");
        if (batchMetaRepository.isCompletedMonthlyStep(jobName, stepName, targetYearMonth)) {
            // 트릭: 상태를 실패나 NOOP로 만들어서 건너뛰게
            log.debug("jobName:{}, stepName:{}, year-month:{} ====== skip", jobName, stepExecution, targetYearMonth);
            stepExecution.setTerminateOnly(); // step 자체 실행 취소
        }
    }
}
