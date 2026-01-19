package com.bb.eodi.address.job.listener;

import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 주소 연계 최신화 StepExecutionListener
 */
@RequiredArgsConstructor
public class AddressUpdateStepExecutionListener implements StepExecutionListener {

    // 대상 기준정보명
    private final String targetReferenceName;

    private final ReferenceVersionRepository referenceVersionRepository;


    @Override
    @Transactional
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();

        LocalDate targetDate = (LocalDate) stepExecutionContext.get("targetDate");
        referenceVersionRepository.updateEffectiveDateByReferenceVersionName(targetDate, targetReferenceName);

        return stepExecution.getExitStatus();
    }
}
