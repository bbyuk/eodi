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

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();

        String dealMonth = jobCtx.getString(DEAL_MONTH.name());

        // API 요청 대상 법정동 목록 조회
        List<MonthlyLoadTargetLegalDongDto> monthlyLoadTargetLegalDongList = legalDongRepository.findAllSummary()
                .stream()
                .map(legalDongSummaryView -> new MonthlyLoadTargetLegalDongDto(
                        legalDongSummaryView.sidoCode() + legalDongSummaryView.sigunguCode()))
                .collect(Collectors.toList());

        // temp file 생성
        Path tempFilePath = Files.createTempFile(TEMP_FILE.name(), null);

        /**
         * 전국 대상 API 요청 후 파일에 작성
         * 실패시 생성된 임시 파일 삭제
         */
        try(BufferedWriter bw = Files.newBufferedWriter(tempFilePath, StandardOpenOption.APPEND)) {
            for (MonthlyLoadTargetLegalDongDto monthlyLoadTargetLegalDongDto : monthlyLoadTargetLegalDongList) {

                DealDataResponse<T> apiResponse = fetchByType(new DealDataQuery(monthlyLoadTargetLegalDongDto.regionCode(), dealMonth));
                List<T> apartmentSellDataItems = apiResponse.body().getItems();

                log.debug("write to file for region code : {}", monthlyLoadTargetLegalDongDto.regionCode());

                for (Object item : apartmentSellDataItems) {
                    bw.write(objectMapper.writeValueAsString(item));
                    bw.newLine();
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            Files.delete(tempFilePath);
            log.info("ApartmentSaleApiFetchStepTasklet -> temp file deleted. file={}", tempFilePath);
            throw new RuntimeException(e);
        }

        /**
         * 임시 파일명 jobContext에 저장
         */
        jobCtx.putString(TEMP_FILE.name(), tempFilePath.toString());


        return RepeatStatus.FINISHED;
    }

    @SuppressWarnings("unchecked")
    private DealDataResponse<T> fetchByType(DealDataQuery query) {
        if(targetClass.equals(ApartmentLeaseDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getApartmentLeaseData(query);
        }
        else if (targetClass.equals(ApartmentPreSaleRightSellDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getApartmentPresaleRightSellData(query);
        }
        else if (targetClass.equals(ApartmentSellDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getApartmentSellData(query);
        }
        else if (targetClass.equals(MultiHouseholdLeaseDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getMultiHouseholdLeaseData(query);
        }
        else if (targetClass.equals(MultiHouseholdSellDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getMultiHouseholdSellData(query);
        }
        else if (targetClass.equals(MultiUnitDetachedLeaseDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getMultiUnitDetachedLeaseData(query);
        }
        else if (targetClass.equals(MultiUnitDetachedSellDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getMultiUnitDetachedSellData(query);
        }
        else if (targetClass.equals(OfficetelLeaseDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getOfficetelLeaseData(query);
        }
        else if (targetClass.equals(OfficetelSellDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getOfficetelSellData(query);
        }

        throw new RuntimeException("Unsupported target class: " + targetClass);
    }
}
