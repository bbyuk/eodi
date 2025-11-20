package com.bb.eodi.deal.job.config;

import com.bb.eodi.deal.job.dto.*;
import com.bb.eodi.deal.job.reader.RealEstateDealDataItemStreamReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MonthlyDealDataLoadChunkConfig {

    private final ObjectMapper objectMapper;
    
    /**
     * 아파트 매매 데이터 적재 chunk ItemReader
     * @param tempFilePath jobExecutionContext 값 임시 파일 경로
     * @return 아파트 매매 데이터 적재 chunk ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<ApartmentSellDataItem> apartmentSellDataItemReader(@Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath) {
        return new RealEstateDealDataItemStreamReader<>(ApartmentSellDataItem.class, Paths.get(tempFilePath), objectMapper);
    }

    /**
     * 아파트 분양권/입주권 매매 데이터 적재 chunk ItemReader
     * @param tempFilePath jobExecutionContext 값 임시 파일 경로
     * @return 아파트 분양권/입주권 매매 데이터 적재 chunk ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<ApartmentPresaleRightSellDataItem> apartmentPresaleRightSellDataItemReader(@Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath) {
        return new RealEstateDealDataItemStreamReader<>(ApartmentPresaleRightSellDataItem.class, Paths.get(tempFilePath), objectMapper);
    }

    /**
     * 단독/다가구주택 매매 데이터 ItemStreamReader
     * @param tempFilePath jobExecutionContext 값 임시 파일 경로
     * @return 단독/다가구주택 매매 데이터 ItemStreamReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<MultiUnitDetachedSellDataItem> multiUnitDetachedSellDataItemReader(@Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath) {
        return new RealEstateDealDataItemStreamReader<>(MultiUnitDetachedSellDataItem.class, Paths.get(tempFilePath), objectMapper);
    }

    /**
     * 연립/다세대주택 매매 데이터 적재 배치 chunk ItemReader
     * @param tempFilePath jobExecutionContext 값 임시 파일 경로
     * @return 연립/다세대주택 매매 데이터 적재 배치 chunk ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<MultiHouseholdHouseSellDataItem> multiHouseholdSellDataItemReader(@Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath) {
        return new RealEstateDealDataItemStreamReader<>(MultiHouseholdHouseSellDataItem.class, Paths.get(tempFilePath), objectMapper);
    }

    /**
     * 오피스텔 매매 실거래가 데이터 적재 배치 chunk ItemReader
     * @param tempFilePath jobExecutionContext 값 임시 파일 경로
     * @return 오피스텔 매매 실거래가 데이터 적재 배치 chunk ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<OfficetelSellDataItem> officetelSellDataItemReader(@Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath) {
        return new RealEstateDealDataItemStreamReader<>(OfficetelSellDataItem.class, Paths.get(tempFilePath), objectMapper);
    }

    /**
     * 아파트 임대차 실거래가 데이터 적재 배치 chunk step ItemReader
     * @param tempFilePath jobExecutionContext 값 임시 파일 경로
     * @return 아파트 임대차 실거래가 데이터 적재 배치 chunk step ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<ApartmentLeaseDataItem> apartmentLeaseDataItemReader(@Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath) {
        return new RealEstateDealDataItemStreamReader<>(ApartmentLeaseDataItem.class, Paths.get(tempFilePath), objectMapper);
    }


    /**
     * 단독/다가구주택 임대차 실거래가 데이터 적재 배치 chunk step ItemReader
     * @param tempFilePath jobExecutionContext 값 임시 파일 경로
     * @return 단독/다가구주택 임대차 실거래가 데이터 적재 배치 chunk step ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<MultiUnitDetachedLeaseDataItem> multiUnitDetachedLeaseDataItemReader(@Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath) {
        return new RealEstateDealDataItemStreamReader<>(MultiUnitDetachedLeaseDataItem.class, Paths.get(tempFilePath), objectMapper);
    }

    /**
     * 연립/다세대주택 임대차 실거래가 데이터 적재 배치 chunk step ItemReader
     * @param tempFilePath jobExecutionContext 값 임시 파일 경로
     * @return 연립/다세대주택 임대차 실거래가 데이터 적재 배치 chunk step ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<MultiHouseholdHouseLeaseDataItem> multiHouseholdHouseLeaseDataItemReader(@Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath) {
        return new RealEstateDealDataItemStreamReader<>(MultiHouseholdHouseLeaseDataItem.class, Paths.get(tempFilePath), objectMapper);
    }

    /**
     * 오피스텔 임대차 실거래가 데이터 적재 배치 chunk step ItemReader
     * @param tempFilePath jobExecutionContext 값 임시 파일 경로
     * @return 오피스텔 임대차 실거래가 데이터 적재 배치 chunk step ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<OfficetelLeaseDataItem> officetelLeaseDataItemReader(@Value("#{jobExecutionContext['TEMP_FILE']}") String tempFilePath) {
        return new RealEstateDealDataItemStreamReader<>(OfficetelLeaseDataItem.class, Paths.get(tempFilePath), objectMapper);
    }
}
