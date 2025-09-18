package com.bb.eodi.batch.job.deal.load.config;

import com.bb.eodi.batch.job.deal.load.reader.RealEstateDealDataItemStreamReader;
import com.bb.eodi.port.out.deal.dto.ApartmentPresaleRightSellDataItem;
import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
import com.bb.eodi.port.out.deal.dto.MultiUnitDetachedSellDataItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

/**
 * 부동산 실거래가 데이터 적재 배치 Chunk 구성요소 설정
 */
@Configuration
public class MonthlyDealDataLoadChunkConfig {

    /**
     * 아파트 매매 데이터 적재 chunk ItemReader
     * @param tempFilePath jobExecutionContext 변수 - 임시 파일 경로
     * @param objectMapper objectMapper
     * @return 아파트 매매 데이터 적재 chunk ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<ApartmentSellDataItem> apartmentSellDataItemReader(
            @Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath,
            ObjectMapper objectMapper
    ) {
        return new RealEstateDealDataItemStreamReader<>(Paths.get(tempFilePath), objectMapper, ApartmentSellDataItem.class);
    }

    /**
     * 아파트 분양권/입주권 매매 데이터 적재 chunk ItemReader
     * @param tempFilePath jobExecutionContext 변수 - 임시 파일 경로
     * @param objectMapper objectMapper
     * @return 아파트 분양권/입주권 매매 데이터 적재 chunk ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<ApartmentPresaleRightSellDataItem> apartmentPresaleRightSellDataItemReader(
            @Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath,
            ObjectMapper objectMapper
    ) {
        return new RealEstateDealDataItemStreamReader<>(Paths.get(tempFilePath), objectMapper, ApartmentPresaleRightSellDataItem.class);
    }

    /**
     * 단독/다가구주택 매매 데이터 ItemStreamReader
     * @param tempFilePath jobExecutionContext 변수 - 임시 파일 경로
     * @param objectMapper objectMapper
     * @return 단독/다가구주택 매매 데이터 ItemStreamReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<MultiUnitDetachedSellDataItem> multiUnitDetachedSellDataItemReader(
            @Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath,
            ObjectMapper objectMapper
    ) {
        return new RealEstateDealDataItemStreamReader<>(Paths.get(tempFilePath), objectMapper, MultiUnitDetachedSellDataItem.class);
    }
}
