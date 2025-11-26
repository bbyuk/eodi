package com.bb.eodi.deal.job.listener;

import com.bb.eodi.deal.domain.eunms.MonthlyDealDataLoadJobKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Chunk step 완료 후 temp file 삭제 처리를 위한 StepExecutionListener
 */
@Slf4j
@RequiredArgsConstructor
public class TempFileCleanupStepListener<T> implements StepExecutionListener {

    private final Class<T> targetClass;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExecutionContext jobCtx = stepExecution.getJobExecution().getExecutionContext();

        if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
            if (jobCtx.containsKey(MonthlyDealDataLoadJobKey.tempFile(targetClass))) {

                Path tempFilePath = Paths.get(jobCtx.getString(MonthlyDealDataLoadJobKey.tempFile(targetClass)));

                try {
                    Files.deleteIfExists(tempFilePath);
                    log.info("Step 완료 -> temp file 삭제. file={}", tempFilePath);
                }
                catch (Exception e) {
                    log.error("temp file 삭제 실패. file={}", tempFilePath, e);
                }
            }
            else {
                log.warn("StepExecutionContext에 temp file path({}}가 없음.", MonthlyDealDataLoadJobKey.tempFile(targetClass));
            }
        }
        else {
            log.warn("Step이 실패하여 temp file을 삭제하지 않음. status={}", stepExecution.getStatus());
        }

        return stepExecution.getExitStatus();
    }
}
