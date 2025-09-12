package com.bb.eodi.batch.deal.load.tasklet;

import com.bb.eodi.batch.deal.load.MonthlyDealDataLoadJobKey;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * 아파트 매매 데이터 API 요청 Tasklet
 */
@StepScope
@Component
@RequiredArgsConstructor
public class ApartmentSaleApiFetchTasklet implements Tasklet {

    private LegalDongRepository legalDongRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();
        String dealMonth = jobCtx.getString(MonthlyDealDataLoadJobKey.DEAL_MONTH.name());


        return null;
    }
}
