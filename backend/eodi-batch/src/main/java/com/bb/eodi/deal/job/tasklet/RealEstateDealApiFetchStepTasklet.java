package com.bb.eodi.deal.job.tasklet;

import com.bb.eodi.deal.domain.eunms.MonthlyDealDataLoadJobKey;
import com.bb.eodi.deal.job.dto.*;
import com.bb.eodi.integration.gov.deal.DealDataApiClient;
import com.bb.eodi.integration.gov.deal.dto.DealDataQuery;
import com.bb.eodi.integration.gov.deal.dto.DealDataResponse;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

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

//    @Value("#{jobParameters['year-month']}")
    @Value("#{jobExecutionContext['yearMonth']}")
    private String dealMonth;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();

        // 이전에 생성되어 있는 파일이 있는지 확인 후 API 요청 스킵여부 결정
        if (jobCtx.containsKey(MonthlyDealDataLoadJobKey.tempFile(targetClass))) {
            if (Files.exists(Paths.get(jobCtx.getString(MonthlyDealDataLoadJobKey.tempFile(targetClass))))) {
                log.info("이전 실행에서 생성된 임시파일 발견. API 요청 skip. file={}", jobCtx.getString(MonthlyDealDataLoadJobKey.tempFile(targetClass)));
                return RepeatStatus.FINISHED;
            }
            else {
                log.warn("JobExecutionContext에는 경로가 있으나 실제 파일 없음. 새로 생성");
            }
        }



        // API 요청 대상 법정동 목록 조회
        List<MonthlyLoadTargetLegalDongDto> monthlyLoadTargetLegalDongList = legalDongRepository.findAllSummary()
                .stream()
                .map(legalDongSummaryView -> new MonthlyLoadTargetLegalDongDto(
                        legalDongSummaryView.sidoCode() + legalDongSummaryView.sigunguCode()))
                .collect(Collectors.toList());

        // temp file 생성
        Path tempFilePath = Files.createTempFile(MonthlyDealDataLoadJobKey.tempFile(targetClass), null);

        /**
         * 전국 대상 API 요청 후 파일에 작성
         * 실패시 생성된 임시 파일 삭제
         */
        try (BufferedWriter bw = Files.newBufferedWriter(tempFilePath, StandardOpenOption.APPEND)) {
            for (MonthlyLoadTargetLegalDongDto monthlyLoadTargetLegalDongDto : monthlyLoadTargetLegalDongList) {
                /**
                 * fetch를 1회 실행 해 totalCount 가져오기
                 *
                 */
                DealDataResponse<T> countResponse = fetchByType(
                        new DealDataQuery(
                                monthlyLoadTargetLegalDongDto.regionCode(),
                                dealMonth,
                                0,
                                0
                        )
                );
                int totalCount = countResponse.body().getTotalCount();
                int maxPageNum = (totalCount / pageSize) + 1;

                log.debug("totalRowCount : {} at region code : {}", totalCount, monthlyLoadTargetLegalDongDto.regionCode());
                log.debug("maxPageNum:{}", maxPageNum);

                for (int pageNum = 1; pageNum <= maxPageNum; pageNum++) {
                    log.debug("pageNum : {}", pageNum);
                    DealDataResponse<T> apiResponse = fetchByType(
                            new DealDataQuery(
                                    monthlyLoadTargetLegalDongDto.regionCode(),
                                    dealMonth,
                                    pageSize,
                                    pageNum)
                    );
                    List<T> apartmentSellDataItems = apiResponse.body().getItems();

                    for (Object item : apartmentSellDataItems) {
                        bw.write(objectMapper.writeValueAsString(item));
                        bw.newLine();
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Files.delete(tempFilePath);
            log.info("ApartmentSaleApiFetchStepTasklet -> temp file deleted. file={}", tempFilePath);
            throw new RuntimeException(e);
        }

        /**
         * 임시 파일명 jobContext에 저장
         */
        jobCtx.putString(MonthlyDealDataLoadJobKey.tempFile(targetClass), tempFilePath.toString());


        return RepeatStatus.FINISHED;
    }

    @SuppressWarnings("unchecked")
    private DealDataResponse<T> fetchByType(DealDataQuery query) {
        if (targetClass.equals(ApartmentLeaseDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getApartmentLeaseData(query);
        } else if (targetClass.equals(ApartmentPresaleRightSellDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getApartmentPresaleRightSellData(query);
        } else if (targetClass.equals(ApartmentSellDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getApartmentSellData(query);
        } else if (targetClass.equals(MultiHouseholdHouseLeaseDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getMultiHouseholdLeaseData(query);
        } else if (targetClass.equals(MultiHouseholdHouseSellDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getMultiHouseholdSellData(query);
        } else if (targetClass.equals(MultiUnitDetachedLeaseDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getMultiUnitDetachedLeaseData(query);
        } else if (targetClass.equals(MultiUnitDetachedSellDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getMultiUnitDetachedSellData(query);
        } else if (targetClass.equals(OfficetelLeaseDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getOfficetelLeaseData(query);
        } else if (targetClass.equals(OfficetelSellDataItem.class)) {
            return (DealDataResponse<T>) dealDataApiClient.getOfficetelSellData(query);
        }

        throw new RuntimeException("Unsupported target class: " + targetClass);
    }
}
