package com.bb.eodi.deal.job.service;

import com.bb.eodi.deal.domain.eunms.MonthlyDealDataLoadJobKey;
import com.bb.eodi.deal.domain.type.DealType;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.job.dto.*;
import com.bb.eodi.integration.gov.config.GovernmentDataApiProperties;
import com.bb.eodi.integration.gov.deal.DealDataApiClient;
import com.bb.eodi.integration.gov.deal.dto.DealDataQuery;
import com.bb.eodi.integration.gov.deal.dto.DealDataResponse;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

import static com.bb.eodi.deal.domain.type.DealType.LEASE;
import static com.bb.eodi.deal.domain.type.DealType.SELL;

/**
 * 부동산 실거래가 데이터 API 연동 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DealDataApiIntegrationService {
    private final LegalDongRepository legalDongRepository;
    private final DealDataApiClient dealDataApiClient;
    private final ObjectMapper objectMapper;
    private final GovernmentDataApiProperties governmentDataApiProperties;

    /**
     * 부동산 실거래가 데이터 API를 호출해 데이터를 요청하고 임시파일에 저장한 후 생성된 임시파일 Path를 리턴한다.
     * @param yearMonth     API 요청 대상 년월
     * @param dealType      API 요청 대상 거래 유형
     * @param housingType   API 요청 대상 주택 유형
     * @return
     */
    @Transactional(readOnly = true)
    public Path createDealDataTempFileWithApiFetching(String yearMonth, DealType dealType, HousingType housingType) {
        // API 요청 대상 법정동 목록 조회
        List<MonthlyLoadTargetLegalDongDto> monthlyLoadTargetLegalDongList = legalDongRepository.findAllSummary()
                .stream()
                .map(legalDongSummaryView -> new MonthlyLoadTargetLegalDongDto(
                        legalDongSummaryView.sidoCode() + legalDongSummaryView.sigunguCode()))
                .collect(Collectors.toList());

        // temp file 생성
        Path tempFilePath;

        try {
            tempFilePath = Files.createTempFile(dealType.name() + "-" + housingType.name(), null);
        }
        catch(IOException e) {
            log.error("임시 파일 생성중 오류가 발생했습니다.");
            throw new RuntimeException("임시 파일 생성중 오류가 발생했습니다.");
        }
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

                DealDataQuery countQuery = new DealDataQuery(
                        monthlyLoadTargetLegalDongDto.regionCode(),
                        yearMonth,
                        0,
                        0
                );

                DealDataResponse countResponse = fetchDealDataApi(dealType, housingType, countQuery);

                int totalCount = countResponse.body().getTotalCount();
                int maxPageNum = (totalCount / governmentDataApiProperties.pageSize()) + 1;

                log.debug("totalRowCount : {} at region code : {}", totalCount, monthlyLoadTargetLegalDongDto.regionCode());
                log.debug("maxPageNum:{}", maxPageNum);

                for (int pageNum = 1; pageNum <= maxPageNum; pageNum++) {
                    log.debug("pageNum : {}", pageNum);
                    DealDataQuery query = new DealDataQuery(
                            monthlyLoadTargetLegalDongDto.regionCode(),
                            yearMonth,
                            governmentDataApiProperties.pageSize(),
                            pageNum);

                    DealDataResponse apiResponse = fetchDealDataApi(dealType, housingType, query);

                    List apartmentSellDataItems = apiResponse.body().getItems();

                    for (Object item : apartmentSellDataItems) {
                        bw.write(objectMapper.writeValueAsString(item));
                        bw.newLine();
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info("ApartmentSaleApiFetchStepTasklet -> temp file deleted. file={}", tempFilePath);

            try {
                Files.delete(tempFilePath);
            }
            catch(IOException ioe) {
                log.error("임시 파일을 삭제하는 중 오류가 발생했습니다. 수기로 삭제해주세요. ====== {}", tempFilePath);
            }

            throw new RuntimeException(e);
        }

        return tempFilePath;
    }

    /**
     * 부동산 실거래가 데이터 API를 호출해 데이터를 fetch한다.
     * @param dealType      거래유형
     * @param housingType   주택유형
     * @param query         조회쿼리
     * @return              API 응답
     */
    private DealDataResponse fetchDealDataApi(DealType dealType, HousingType housingType, DealDataQuery query) {
        DealDataResponse response;

        if (dealType == SELL) {
            if (housingType == HousingType.APT) {
                response = dealDataApiClient.getApartmentSellData(query);
            }
            else if (housingType == HousingType.PRESALE_RIGHT) {
                response = dealDataApiClient.getApartmentPresaleRightSellData(query);
            }
            else if (housingType == HousingType.MULTI_HOUSEHOLD_HOUSE) {
                response = dealDataApiClient.getMultiHouseholdSellData(query);
            }
            else if (housingType == HousingType.MULTI_UNIT_HOUSE) {
                response = dealDataApiClient.getMultiUnitDetachedSellData(query);
            }
            else if (housingType == HousingType.OFFICETEL) {
                response = dealDataApiClient.getOfficetelSellData(query);
            }
            else {
                log.error("입력된 주택 유형 ====== {}", housingType);
                throw new RuntimeException("지원하지 않는 주택 유형입니다.");
            }
        }
        else if (dealType == LEASE) {
            if (housingType == HousingType.APT) {
                response = dealDataApiClient.getApartmentLeaseData(query);
            }
            else if (housingType == HousingType.MULTI_HOUSEHOLD_HOUSE) {
                response = dealDataApiClient.getMultiHouseholdLeaseData(query);
            }
            else if (housingType == HousingType.MULTI_UNIT_HOUSE) {
                response = dealDataApiClient.getMultiUnitDetachedLeaseData(query);
            }
            else if (housingType == HousingType.OFFICETEL) {
                response = dealDataApiClient.getOfficetelLeaseData(query);
            }
            else {
                log.error("입력된 주택 유형 ====== {}", housingType);
                throw new RuntimeException("지원하지 않는 주택 유형입니다.");
            }
        }
        else {
            log.error("입력된 거래 유형 ====== {}", dealType);
            throw new RuntimeException("지원하지 않는 거래 유형입니다.");
        }

        return response;
    }
}
