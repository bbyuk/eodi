package com.bb.eodi.deal.job.decider;

import com.bb.eodi.deal.domain.type.DealType;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.domain.utils.FormattingUtils;
import com.bb.eodi.deal.job.config.DealJobContextKey;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.item.ExecutionContext;

import java.time.LocalDate;

import static com.bb.eodi.deal.domain.utils.FormattingUtils.toReferenceVersionTargetName;
import static com.bb.eodi.deal.job.config.DealJobContextKey.*;

/**
 * 실거래가 데이터 최신화 배치에서 다음 연월을 조회할 지 여부 flow 분기 JobExecutionDecider
 */
@RequiredArgsConstructor
public class TargetYearMonthDecider implements JobExecutionDecider {

    private final HousingType housingType;
    private final DealType dealType;

    @Override
    public FlowExecutionStatus decide(
            JobExecution jobExecution,
            StepExecution stepExecution
    ) {
        ExecutionContext ctx = jobExecution.getExecutionContext();

        LocalDate lastUpdateDate = (LocalDate) ctx.get(FormattingUtils.toJobExecutionContextKey(toReferenceVersionTargetName(housingType, dealType), LAST_UPDATED_DATE));
        LocalDate today = LocalDate.now();

        if (today.isAfter(lastUpdateDate)) {
            return new FlowExecutionStatus("CONTINUE");
        }
        return FlowExecutionStatus.COMPLETED;
    }
}