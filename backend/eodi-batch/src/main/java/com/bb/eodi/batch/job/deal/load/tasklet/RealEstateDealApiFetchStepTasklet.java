package com.bb.eodi.batch.job.deal.load.tasklet;

import com.bb.eodi.batch.job.deal.load.MonthlyDealDataLoadJobKey;
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

import static com.bb.eodi.batch.job.deal.load.MonthlyDealDataLoadJobKey.*;

/**
 * 부동산 거래 데이터 API 요청 Tasklet
 */
@Slf4j
@RequiredArgsConstructor
public class RealEstateDealApiFetchStepTasklet<T> implements Tasklet {

    private final Class<T> targetClass;

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
        jobCtx.putString(
                TEMP_FILE.name(),
                getTempFileName(
                        jobCtx.getString(TEMP_FILE_ROOT.name()),
                        jobCtx.getString(DEAL_MONTH.name())
                ).toString()
        );


        return RepeatStatus.FINISHED;
    }

    /**
     * 대상 년월을 파라미터로 받아 임시파일명을 리턴한다.
     *
     * @param yearMonth 대상년월
     * @return 임시파일명
     */
    private Path getTempFileName(String tempFileRoot, String yearMonth) {
        if (targetClass.equals(ApartmentLeaseDataItem.class)) {
            return Paths.get(tempFileRoot, yearMonth, "lease_apt.csv");
        } else if (targetClass.equals(ApartmentPresaleRightSellDataItem.class)) {
            return Paths.get(tempFileRoot, yearMonth, "sell_apr.csv");
        } else if (targetClass.equals(ApartmentSellDataItem.class)) {
            return Paths.get(tempFileRoot, yearMonth, "sell_apt.csv");
        } else if (targetClass.equals(MultiHouseholdHouseLeaseDataItem.class)) {
            return Paths.get(tempFileRoot, yearMonth, "lease_mhh.csv");
        } else if (targetClass.equals(MultiHouseholdHouseSellDataItem.class)) {
            return Paths.get(tempFileRoot, yearMonth, "sell_mhh.csv");
        } else if (targetClass.equals(MultiUnitDetachedLeaseDataItem.class)) {
            return Paths.get(tempFileRoot, yearMonth, "lease_mud.csv");
        } else if (targetClass.equals(MultiUnitDetachedSellDataItem.class)) {
            return Paths.get(tempFileRoot, yearMonth, "sell_mud.csv");
        } else if (targetClass.equals(OfficetelLeaseDataItem.class)) {
            return Paths.get(tempFileRoot, yearMonth, "lease_off.csv");
        } else if (targetClass.equals(OfficetelSellDataItem.class)) {
            return Paths.get(tempFileRoot, yearMonth, "sell_off.csv");
        }

        throw new RuntimeException("Unsupported target class: " + targetClass);
    }
}
