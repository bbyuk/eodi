package com.bb.eodi.batch.deal.load.tasklet;

import com.bb.eodi.batch.deal.load.MonthlyDealDataLoadJobKey;
import com.bb.eodi.domain.deal.dto.MonthlyLoadTargetLegalDongDto;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 아파트 매매 데이터 API 요청 Tasklet
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class ApartmentSaleApiFetchStepTasklet implements Tasklet {

    private final LegalDongRepository legalDongRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();
        String dealMonth = jobCtx.getString(MonthlyDealDataLoadJobKey.DEAL_MONTH.name());

        // API 요청 대상 법정동 목록 조회
        List<MonthlyLoadTargetLegalDongDto> monthlyLoadTargetLegalDongList = legalDongRepository.findAllSummary()
                .stream()
                .map(legalDongSummaryView -> new MonthlyLoadTargetLegalDongDto(
                        legalDongSummaryView.sidoCode() + legalDongSummaryView.sigunguCode()))
                .collect(Collectors.toList());

        for (MonthlyLoadTargetLegalDongDto monthlyLoadTargetLegalDongDto : monthlyLoadTargetLegalDongList) {
            log.debug(monthlyLoadTargetLegalDongDto.regionCode());
        }


        return null;
    }
}
