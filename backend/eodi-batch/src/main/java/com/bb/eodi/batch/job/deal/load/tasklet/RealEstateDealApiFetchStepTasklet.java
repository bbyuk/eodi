package com.bb.eodi.batch.job.deal.load.tasklet;

import com.bb.eodi.domain.deal.dto.MonthlyLoadTargetLegalDongDto;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import com.bb.eodi.infrastructure.api.deal.DealDataApiClient;
import com.bb.eodi.port.out.deal.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

import static com.bb.eodi.batch.job.deal.load.MonthlyDealDataLoadJobKey.DEAL_MONTH;
import static com.bb.eodi.batch.job.deal.load.MonthlyDealDataLoadJobKey.TEMP_FILE;

/**
 * 부동산 거래 데이터 API 요청 Tasklet
 */
@Slf4j
@RequiredArgsConstructor
public class RealEstateDealApiFetchStepTasklet<T> implements Tasklet {

    private final Class<T> targetClass;
    private final LegalDongRepository legalDongRepository;
    private final DealDataApiClient dealDataApiClient;
    private final ObjectMapper objectMapper;
    private final int pageSize;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();

        /**
         * 1. jobExecutionContext parameter로 아래 항목 받아옴
         *      1.1. 대상 주택유형
         *      1.2. yearMonth
         * 2. 파라미터 조합해 대상 파일 경로 get
         * 3. jobExecutionContext에 TEMP_FILE.name() key로 setString()
         */




        return RepeatStatus.FINISHED;
    }

    /**
     * 대상 년월을 파라미터로 받아 임시파일명을 리턴한다.
     * @param yearMonth 대상년월
     * @return 임시파일명
     */
    private String getTempFileName(String yearMonth) {
        if (targetClass.equals(ApartmentLeaseDataItem.class)) {
            return "lease-apt-" + yearMonth + ".csv";
        } else if (targetClass.equals(ApartmentPresaleRightSellDataItem.class)) {
            return "sell-apr-" + yearMonth + ".csv";
        } else if (targetClass.equals(ApartmentSellDataItem.class)) {
            return "sell-apt-" + yearMonth + ".csv";
        } else if (targetClass.equals(MultiHouseholdHouseLeaseDataItem.class)) {
            return "lease-mhh-" + yearMonth + ".csv";
        } else if (targetClass.equals(MultiHouseholdHouseSellDataItem.class)) {
            return "sell-mhh-" + yearMonth + ".csv";
        } else if (targetClass.equals(MultiUnitDetachedLeaseDataItem.class)) {
            return "lease-mud-" + yearMonth + ".csv";
        } else if (targetClass.equals(MultiUnitDetachedSellDataItem.class)) {
            return "sell-mud-" + yearMonth + ".csv";
        } else if (targetClass.equals(OfficetelLeaseDataItem.class)) {
            return "lease-off-" + yearMonth + ".csv";
        } else if (targetClass.equals(OfficetelSellDataItem.class)) {
            return "sell-off-" + yearMonth + ".csv";
        }

        throw new RuntimeException("Unsupported target class: " + targetClass);
    }
}
