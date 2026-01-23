package com.bb.eodi.deal.job.tasklet;

import com.bb.eodi.deal.domain.type.DealType;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.domain.utils.FormattingUtils;
import com.bb.eodi.deal.job.config.DealJobContextKey;
import com.bb.eodi.ops.domain.entity.ReferenceVersion;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.bb.eodi.deal.domain.utils.FormattingUtils.*;
import static com.bb.eodi.deal.job.config.DealJobContextKey.*;

/**
 * 부동산 매매 데이터 적재 배치 전처리 Step tasklet
 */
@Slf4j
@RequiredArgsConstructor
public class DealDataLoadPreprocessStepTasklet implements Tasklet {

    private final ReferenceVersionRepository referenceVersionRepository;

    private final HousingType housingType;

    private final DealType dealType;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();

        String referenceVersionTargetName = toReferenceVersionTargetName(housingType, dealType);

        ReferenceVersion dealUpdateVersion = referenceVersionRepository.findByTargetName(referenceVersionTargetName)
                .orElseGet(() -> {
                    // 3개월 이전 첫 날로 변경
                    LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3).withDayOfMonth(1);
                    referenceVersionRepository.insert(ReferenceVersion.builder()
                                    .targetName(referenceVersionTargetName)
                                    .effectiveDate(threeMonthsAgo)
                                    .updatedBy("batch")
                                    .updatedAt(LocalDateTime.now())
                            .build());
                    return referenceVersionRepository.findByTargetName(referenceVersionTargetName).orElseThrow(() -> new RuntimeException("문제가 발생했습니다."));
                });


        if (!dealUpdateVersion.getEffectiveDate().isBefore(LocalDate.now())) {
            log.error("최근 업데이트 일자 : {}",  dealUpdateVersion.getEffectiveDate());
            log.error("오늘 날짜 : {}", LocalDate.now());

            throw new JobInterruptedException("이미 실거래가 데이터가 최신 버전입니다.");
        }

        jobCtx.put("toDate", LocalDate.now());
        jobCtx.put(toJobExecutionContextKey(referenceVersionTargetName, LAST_UPDATED_DATE), dealUpdateVersion.getEffectiveDate());

        String yearMonth = toYearMonth(dealUpdateVersion.getEffectiveDate());

        jobCtx.put(toJobExecutionContextKey(referenceVersionTargetName, TARGET_YEAR_MONTH), yearMonth);

        return RepeatStatus.FINISHED;
    }
}
