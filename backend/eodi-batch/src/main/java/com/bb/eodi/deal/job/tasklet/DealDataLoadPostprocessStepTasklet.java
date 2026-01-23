package com.bb.eodi.deal.job.tasklet;


import com.bb.eodi.deal.domain.type.DealType;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.util.Set;

import static com.bb.eodi.deal.domain.utils.FormattingUtils.*;
import static com.bb.eodi.deal.job.config.DealJobContextKey.LAST_UPDATED_DATE;
import static com.bb.eodi.deal.job.config.DealJobContextKey.UPDATED_YEAR_MONTH;

/**
 * 부동산 매매 데이터 적재 배치 후처리 step tasklet
 */
@Slf4j
@RequiredArgsConstructor
public class DealDataLoadPostprocessStepTasklet implements Tasklet {

    private final ReferenceVersionRepository referenceVersionRepository;

    private final HousingType housingType;
    private final DealType dealType;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();
        String referenceVersionTargetName = toReferenceVersionTargetName(housingType, dealType);

        Set<String> updatedYearMonth = (Set<String>) jobCtx.get(UPDATED_YEAR_MONTH.name());

        String jobExecutionContextKey = toJobExecutionContextKey(referenceVersionTargetName, LAST_UPDATED_DATE);

        LocalDate lastUpdateDate = (LocalDate) jobCtx.get(jobExecutionContextKey);
        LocalDate today = LocalDate.now();

        if (!lastUpdateDate.isBefore(today)) {
            log.info("이미 업데이트가 종료되었습니다.");
            return RepeatStatus.FINISHED;
        }

        LocalDate currentUpdateDate = lastUpdateDate.getMonthValue() != today.getMonthValue()
                ? lastUpdateDate.plusMonths(1).withDayOfMonth(1)     // 월이 다를 경우
                : today;                                                         // 동일한 월


        referenceVersionRepository.updateEffectiveDateByReferenceVersionName(
                currentUpdateDate,
                referenceVersionTargetName
        );

        jobCtx.put(jobExecutionContextKey, currentUpdateDate);

        String yearMonth = toYearMonth(currentUpdateDate);
        updatedYearMonth.add(referenceVersionTargetName + "-" + yearMonth);

        return RepeatStatus.FINISHED;
    }
}
