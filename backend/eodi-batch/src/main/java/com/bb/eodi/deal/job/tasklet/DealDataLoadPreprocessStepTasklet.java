package com.bb.eodi.deal.job.tasklet;

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

/**
 * 부동산 매매 데이터 적재 배치 전처리 Step tasklet
 */
@Slf4j
@RequiredArgsConstructor
public class DealDataLoadPreprocessStepTasklet implements Tasklet {

    private final ReferenceVersionRepository referenceVersionRepository;

    private final String referenceVersionTargetName;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();

        ReferenceVersion dealUpdateVersion = referenceVersionRepository.findByTargetName(referenceVersionTargetName)
                .orElseThrow(() -> new RuntimeException(referenceVersionTargetName + " 기준정보 데이터를 찾지 못했습니다."));


        if (!dealUpdateVersion.getEffectiveDate().isBefore(LocalDate.now())) {
            log.error("최근 업데이트 일자 : {}",  dealUpdateVersion.getEffectiveDate());
            log.error("오늘 날짜 : {}", LocalDate.now());

            throw new JobInterruptedException("이미 실거래가 데이터가 최신 버전입니다.");
        }

        jobCtx.put("toDate", LocalDate.now());
        jobCtx.put(referenceVersionTargetName + "-lastUpdateDate", dealUpdateVersion.getEffectiveDate());

        String yearMonth = new StringBuilder()
                .append(dealUpdateVersion.getEffectiveDate().getYear())
                .append(String.format("%2s", dealUpdateVersion.getEffectiveDate().getMonthValue()).replace(' ', '0'))
                .toString();

        jobCtx.put(referenceVersionTargetName + "-yearMonth", yearMonth);

        return RepeatStatus.FINISHED;
    }
}
