package com.bb.eodi.batch.deal.load.tasklet;

import com.bb.eodi.batch.deal.load.MonthlyDealDataLoadJobKey;
import com.bb.eodi.domain.deal.dto.MonthlyLoadTargetLegalDongDto;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import com.bb.eodi.infrastructure.http.deal.DealDataApiClient;
import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
import com.bb.eodi.port.out.deal.dto.DealDataQuery;
import com.bb.eodi.port.out.deal.dto.DealDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

import static com.bb.eodi.batch.deal.load.MonthlyDealDataLoadJobKey.*;

/**
 * 아파트 매매 데이터 API 요청 Tasklet
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class ApartmentSaleApiFetchStepTasklet implements Tasklet {

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
        Path tempFilePath = Files.createTempFile(jobCtx.getString(APT_SALE_TEMP_FILE.name()), null);

        /**
         * 전국 대상 API 요청 후 파일에 작성
         * 실패시 생성된 임시 파일 삭제
         */
        try(BufferedWriter bw = Files.newBufferedWriter(tempFilePath, StandardOpenOption.APPEND)) {
            for (MonthlyLoadTargetLegalDongDto monthlyLoadTargetLegalDongDto : monthlyLoadTargetLegalDongList) {
                DealDataResponse<ApartmentSellDataItem> apiResponse = dealDataApiClient.getApartmentSellData(new DealDataQuery(monthlyLoadTargetLegalDongDto.regionCode(), dealMonth));
                List<ApartmentSellDataItem> apartmentSellDataItems = apiResponse.body().getItems();

                log.debug("write to file for region code : {}", monthlyLoadTargetLegalDongDto.regionCode());
                for (ApartmentSellDataItem apartment : apartmentSellDataItems) {
                    bw.write(objectMapper.writeValueAsString(apartment));
                    bw.newLine();
                }

            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            Files.delete(tempFilePath);
            throw new RuntimeException(e);
        }
        finally {
            log.debug("temp file {} deleted", tempFilePath);
            Files.delete(tempFilePath);
        }

        return RepeatStatus.FINISHED;
    }
}
