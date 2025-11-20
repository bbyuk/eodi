package com.bb.eodi.deal.job.config;

import com.bb.eodi.core.config.EodiBatchProperties;
import com.bb.eodi.deal.domain.dto.*;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.job.listener.TempFileCleanupStepListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 월별 부동산 실거래가 데이터 적재 배치 Step 설정
 */
@Configuration
@RequiredArgsConstructor
public class MonthlyDealDataLoadStepConfig {

    private final EodiBatchProperties eodiBatchProperties;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final TempFileCleanupStepListener tempFileCleanupStepListener;

    /**
     * 월별 부동산 거래 데이터 적재 batch job 전처리 Step
     *
     * @param monthlyDealDataLoadPreprocessStepTasklet 월별 부동산 거래 데이터 적재 batch job 전처리 Tasklet
     * @return 월별 데이터 적재 batch job 전처리 Step
     */
    @Bean
    public Step monthlyDealDataLoadPreprocessStep(Tasklet monthlyDealDataLoadPreprocessStepTasklet) {
        return new StepBuilder("monthlyDealDataLoadPreprocessStep", jobRepository)
                .tasklet(monthlyDealDataLoadPreprocessStepTasklet, transactionManager)
                .build();
    }

    /**
     * 아파트 매매 데이터 API 요청 step
     *
     * @param apartmentSellApiFetchStepTasklet 아파트 매매 데이터 API 요청 step tasklet
     * @return 아파트 매매 데이터 API 요청 step
     */
    @Bean
    public Step apartmentSellApiFetchStep(Tasklet apartmentSellApiFetchStepTasklet) {
        return new StepBuilder("apartmentSellApiFetchStep", jobRepository)
                .tasklet(apartmentSellApiFetchStepTasklet, transactionManager)
                .build();
    }

    /**
     * 아파트 매매 데이터 적재 step
     *
     * @param apartmentSellDataItemReader    아파트 매매 데이터 적재 chunk ItemReader
     * @param apartmentSellDataItemProcessor 아파트 매매 대이터 적재 chunk ItemProcessor
     * @param realEstateSellItemWriter       부동산 매매 데이터 적재 chunk ItemWriter
     * @return 아파트 매매 데이터 적재 step
     */
    @Bean
    public Step apartmentSellDataLoadStep(
            ItemReader<ApartmentSellDataItem> apartmentSellDataItemReader,
            ItemProcessor<ApartmentSellDataItem, RealEstateSell> apartmentSellDataItemProcessor,
            ItemWriter<RealEstateSell> realEstateSellItemWriter
    ) {
        return new StepBuilder("apartmentSellDataLoadStep", jobRepository)
                .<ApartmentSellDataItem, RealEstateSell>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(apartmentSellDataItemReader)
                .processor(apartmentSellDataItemProcessor)
                .writer(realEstateSellItemWriter)
                .listener(tempFileCleanupStepListener)
                .build();
    }

    /**
     * 아파트 분양권 매매 데이터 API 요청 step
     *
     * @param apartmentPresaleRightSellApiFetchStepTasklet 아파트 분양권 매매 데이터 API 요청 step tasklet
     * @return 아파트 분양권 매매 데이터 API 요청 step
     */
    @Bean
    public Step apartmentPresaleRightSellApiFetchStep(Tasklet apartmentPresaleRightSellApiFetchStepTasklet) {
        return new StepBuilder("apartmentPresaleRightSellApiFetchStep", jobRepository)
                .tasklet(apartmentPresaleRightSellApiFetchStepTasklet, transactionManager)
                .build();
    }

    /**
     * 아파트 분양권 매매 데이터 적재 step
     * @param apartmentPresaleRightSellDataItemItemReader 아파트 분양권 매매 데이터 ItemReader
     * @param apartmentPresaleRightSellDataItemProcessor 아파트 분양권 매매 데이터 ItemProcessor
     * @param realEstateSellItemWriter 부동산 매매 데이터 ItemWriter
     * @return 아파트 분양권 매매 데이터 적재 step
     */
    @Bean
    public Step apartmentPresaleRightSellDataLoadStep(
            ItemReader<ApartmentPresaleRightSellDataItem> apartmentPresaleRightSellDataItemItemReader,
            ItemProcessor<ApartmentPresaleRightSellDataItem, RealEstateSell> apartmentPresaleRightSellDataItemProcessor,
            ItemWriter<RealEstateSell> realEstateSellItemWriter
    ) {
        return new StepBuilder("apartmentPresaleRightSellDataLoadStep", jobRepository)
                .<ApartmentPresaleRightSellDataItem, RealEstateSell>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(apartmentPresaleRightSellDataItemItemReader)
                .processor(apartmentPresaleRightSellDataItemProcessor)
                .writer(realEstateSellItemWriter)
                .listener(tempFileCleanupStepListener)
                .build();
    }

    /**
     * 단독/다가구주택 매매 데이터 API 요청 step
     * @param multiUnitDetachedSellApiFetchStepTasklet 단독/다가구주택 매매 데이터 API 요청 Step Tasklet
     * @return 단독/다가구주택 매매 데이터 API 요청 step
     */
    @Bean
    public Step multiUnitDetachedSellApiFetchStep(Tasklet multiUnitDetachedSellApiFetchStepTasklet) {
        return new StepBuilder("multiUnitDetachedSellApiFetchStep", jobRepository)
                .tasklet(multiUnitDetachedSellApiFetchStepTasklet, transactionManager)
                .build();
    }

    /**
     * 단독/다가구주택 매매 데이터 적재 step
     * @param multiUnitDetachedSellDataItemReader 단독/다가구주택 매매 데이터 chunk ItemReader
     * @param multiUnitDetachedSellDataItemProcessor 단독/다가구주택 매매 데이터 chunk ItemProcessor
     * @param realEstateSellItemWriter 부동산 매매 데이터 chunk ItemWriter
     * @return 단독/다가구주택 매매 데이터 적재 step
     */
    @Bean
    public Step multiUnitDetachedSellDataLoadStep(
            ItemReader<MultiUnitDetachedSellDataItem> multiUnitDetachedSellDataItemReader,
            ItemProcessor<MultiUnitDetachedSellDataItem, RealEstateSell> multiUnitDetachedSellDataItemProcessor,
            ItemWriter<RealEstateSell> realEstateSellItemWriter

    ) {
        return new StepBuilder("multiUnitDetachedSellDataLoadStep", jobRepository)
                .<MultiUnitDetachedSellDataItem, RealEstateSell>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(multiUnitDetachedSellDataItemReader)
                .processor(multiUnitDetachedSellDataItemProcessor)
                .writer(realEstateSellItemWriter)
                .listener(tempFileCleanupStepListener)
                .build();
    }

    /**
     * 연립/다세대주택 매매 데이터 API 요청 step
     * @param multiHouseholdHouseSellApiFetchStepTasklet 연립/다세대주택 매매 데이터 API 요청 step tasklet
     * @return 연립/다세대주택 매매 데이터 API 요청 step
     */
    @Bean
    public Step multiHouseholdHouseSellApiFetchStep(Tasklet multiHouseholdHouseSellApiFetchStepTasklet) {
        return new StepBuilder("multiHouseholdHouseSellApiFetchStep", jobRepository)
                .tasklet(multiHouseholdHouseSellApiFetchStepTasklet, transactionManager)
                .build();
    }

    /**
     * 연립/다세대주택 매매 데이터 적재 step
     * @param multiHouseholdSellDataItemReader 연립/다세대주택 매매 데이터 적재 chunk step ItemReader
     * @param multiHouseholdSellDataItemProcessor 연립/다세대주택 매매 데이터 적재 chunk step ItemProcessor
     * @param realEstateSellItemWriter 부동산 매매 데이터 chunk ItemWriter
     * @return 연립/다세대주택 매매 데이터 적재 step
     */
    @Bean
    public Step multiHouseholdHouseSellDataLoadStep(
            ItemReader<MultiHouseholdHouseSellDataItem> multiHouseholdSellDataItemReader,
            ItemProcessor<MultiHouseholdHouseSellDataItem, RealEstateSell> multiHouseholdSellDataItemProcessor,
            ItemWriter<RealEstateSell> realEstateSellItemWriter
    ) {
        return new StepBuilder("multiHouseholdHouseSellDataLoadStep", jobRepository)
                .<MultiHouseholdHouseSellDataItem, RealEstateSell>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(multiHouseholdSellDataItemReader)
                .processor(multiHouseholdSellDataItemProcessor)
                .writer(realEstateSellItemWriter)
                .listener(tempFileCleanupStepListener)
                .build();
    }

    /**
     * 오피스텔 매매 실거레가 데이터 API 요청 step
     * @param officetelSellApiFetchStepTasklet 오피스텔 매매 실거레가 데이터 API 요청 step tasklet
     * @return 오피스텔 매매 실거레가 데이터 API 요청 step
     */
    @Bean
    public Step officetelSellApiFetchStep(Tasklet officetelSellApiFetchStepTasklet) {
        return new StepBuilder("officetelSellApiFetchStep", jobRepository)
                .tasklet(officetelSellApiFetchStepTasklet, transactionManager)
                .build();
    }

    /**
     * 오피스텔 매매 실거래가 데이터 적재 step
     * @param officetelSellDataItemReader 오피스텔 매매 실거래가 데이터 적재 step chunk ItemReader
     * @param officetelSellDataItemProcessor 오피스텔 매매 실거래가 데이터 적재 step chunk ItemProcessor
     * @param realEstateSellItemWriter 부동산 매매 실거래가 데이터 chunk ItemWriter
     * @return
     */
    @Bean
    public Step officetelSellDataLoadStep(
            ItemReader<OfficetelSellDataItem> officetelSellDataItemReader,
            ItemProcessor<OfficetelSellDataItem, RealEstateSell> officetelSellDataItemProcessor,
            ItemWriter<RealEstateSell> realEstateSellItemWriter
    ) {
        return new StepBuilder("officetelSellDataLoadStep", jobRepository)
                .<OfficetelSellDataItem, RealEstateSell>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(officetelSellDataItemReader)
                .processor(officetelSellDataItemProcessor)
                .writer(realEstateSellItemWriter)
                .listener(tempFileCleanupStepListener)
                .build();
    }

    /**
     * 아파트 임대차 실거래가 API 요청 step
     * @param apartmentLeaseApiFetchStepTasklet 아파트 임대차 실거래가 API 요청 step tasklet
     * @return 아파트 임대차 실거래가 API 요청 step
     */
    @Bean
    public Step apartmentLeaseApiFetchStep(Tasklet apartmentLeaseApiFetchStepTasklet) {
        return new StepBuilder("apartmentLeaseApiFetchStep", jobRepository)
                .tasklet(apartmentLeaseApiFetchStepTasklet, transactionManager)
                .build();
    }

    /**
     * 아파트 임대차 실거래가 데이터 적재 step
     * @param apartmentLeaseDataItemReader 아파트 임대차 데이터 chunk step ItemReader
     * @param apartmentLeaseDataItemProcessor 아파트 임대차 데이터 chunk step ItemProcessor
     * @param realEstateLeaseItemWriter 아파트 임대차 데이터 chunk step ItemProcessor
     * @return 아파트 임대차 실거래가 데이터 적재 step
     */
    @Bean
    public Step apartmentLeaseDataLoadStep(
            ItemReader<ApartmentLeaseDataItem> apartmentLeaseDataItemReader,
            ItemProcessor<ApartmentLeaseDataItem, RealEstateLease> apartmentLeaseDataItemProcessor,
            ItemWriter<RealEstateLease> realEstateLeaseItemWriter
    ) {
        return new StepBuilder("apartmentLeaseDataLoadStep", jobRepository)
                .<ApartmentLeaseDataItem, RealEstateLease>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(apartmentLeaseDataItemReader)
                .processor(apartmentLeaseDataItemProcessor)
                .writer(realEstateLeaseItemWriter)
                .listener(tempFileCleanupStepListener)
                .build();
    }

    /**
     * 단독/다가구주택 전월세 실거래가 데이터 API 요청 step
     * @param multiUnitDetachedLeaseApiFetchStepTasklet 단독/다가구주택 전월세 실거래가 데이터 API 요청 step tasklet
     * @return 단독/다가구주택 전월세 실거래가 데이터 API 요청 step
     */
    @Bean
    public Step multiUnitDetachedLeaseApiFetchStep(Tasklet multiUnitDetachedLeaseApiFetchStepTasklet) {
        return new StepBuilder("multiUnitDetachedLeaseApiFetchStep", jobRepository)
                .tasklet(multiUnitDetachedLeaseApiFetchStepTasklet, transactionManager)
                .build();
    }

    /**
     * 단독/다가구주택 전월세 실거래가 데이터 적재 step
     * @param multiUnitDetachedLeaseDataItemReader 단독/다가구주택 전월세 실거래가 데이터 적재 step chunk ItemReader
     * @param multiUnitDetachedLeaseDataItemProcessor 단독/다가구주택 전월세 실거래가 데이터 적재 step chunk ItemProcessor
     * @param realEstateLeaseItemWriter 부동산 전월세 실거래가 데이터 적재 step chunk ItemWriter
     * @return 단독/다가구주택 전월세 실거래가 데이터 적재 step
     */
    @Bean
    public Step multiUnitDetachedLeaseDataLoadStep(
            ItemReader<MultiUnitDetachedLeaseDataItem> multiUnitDetachedLeaseDataItemReader,
            ItemProcessor<MultiUnitDetachedLeaseDataItem, RealEstateLease> multiUnitDetachedLeaseDataItemProcessor,
            ItemWriter<RealEstateLease> realEstateLeaseItemWriter
    ) {
        return new StepBuilder("multiUnitDetachedLeaseDataLoadStep", jobRepository)
                .<MultiUnitDetachedLeaseDataItem, RealEstateLease>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(multiUnitDetachedLeaseDataItemReader)
                .processor(multiUnitDetachedLeaseDataItemProcessor)
                .writer(realEstateLeaseItemWriter)
                .listener(tempFileCleanupStepListener)
                .build();
    }

    /**
     * 연립/다세대주택 전월세 실거래가 데이터 API 요청 step
     * @param multiHouseholdHouseLeaseApiFetchStepTasklet 연립/다세대주택 전월세 실거래가 데이터 API 요청 step tasklet
     * @return 연립/다세대주택 전월세 실거래가 데이터 API 요청 step
     */
    @Bean
    public Step multiHouseholdHouseLeaseApiFetchStep(Tasklet multiHouseholdHouseLeaseApiFetchStepTasklet) {
        return new StepBuilder("multiHouseholdHouseLeaseApiFetchStep", jobRepository)
                .tasklet(multiHouseholdHouseLeaseApiFetchStepTasklet, transactionManager)
                .build();
    }

    /**
     * 연립/다세대주택 전월세 실거래가 데이터 적재 step
     * @param multiHouseholdHouseLeaseDataItemReader 연립/다세대주택 전월세 실거래가 데이터 적재 chunk ItemReader
     * @param multiHouseholdHouseLeaseDataItemProcessor 연립/다세대주택 전월세 실거래가 데이터 적재 chunk ItemProcessor
     * @param realEstateLeaseItemWriter 부동산 전월세 실거래가 데이터 적재 chunk ItemWriter
     * @return 연립/다세대주택 전월세 실거래가 데이터 적재 step
     */
    @Bean
    public Step multiHouseholdHouseLeaseDataLoadStep(
            ItemReader<MultiHouseholdHouseLeaseDataItem> multiHouseholdHouseLeaseDataItemReader,
            ItemProcessor<MultiHouseholdHouseLeaseDataItem, RealEstateLease> multiHouseholdHouseLeaseDataItemProcessor,
            ItemWriter<RealEstateLease> realEstateLeaseItemWriter
    ) {
        return new StepBuilder("multiHouseholdHouseLeaseDataLoadStep", jobRepository)
                .<MultiHouseholdHouseLeaseDataItem, RealEstateLease>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(multiHouseholdHouseLeaseDataItemReader)
                .processor(multiHouseholdHouseLeaseDataItemProcessor)
                .writer(realEstateLeaseItemWriter)
                .listener(tempFileCleanupStepListener)
                .build();
    }


    /**
     * 오피스텔 전월세 실거래가 데이터 API 요청 step
     * @param officetelLeaseApiFetchStepTasklet 오피스텔 전월세 실거래가 데이터 API 요청 step tasklet
     * @return 오피스텔 전월세 실거래가 데이터 API 요청 step
     */
    @Bean
    public Step officetelLeaseApiFetchStep(Tasklet officetelLeaseApiFetchStepTasklet) {
        return new StepBuilder("officetelLeaseApiFetchStep", jobRepository)
                .tasklet(officetelLeaseApiFetchStepTasklet, transactionManager)
                .build();
    }

    /**
     * 오피스텔 전월세 실거래가 데이터 적재 step
     * @param officetelLeaseDataItemReader 오피스텔 전월세 실거래가 데이터 적재 step chunk ItemReader
     * @param officetelLeaseDataItemProcessor 오피스텔 전월세 실거래가 데이터 적재 step chunk ItemProcessor
     * @param realEstateLeaseItemWriter 부동산 전월세 실거래가 데이터 적재 step chunk ItemWriter
     * @return 오피스텔 전월세 실거래가 데이터 적재 step
     */
    @Bean
    public Step officetelLeaseDataLoadStep(
            ItemReader<OfficetelLeaseDataItem> officetelLeaseDataItemReader,
            ItemProcessor<OfficetelLeaseDataItem, RealEstateLease> officetelLeaseDataItemProcessor,
            ItemWriter<RealEstateLease> realEstateLeaseItemWriter
    ) {
        return new StepBuilder("officetelLeaseDataLoadStep", jobRepository)
                .<OfficetelLeaseDataItem, RealEstateLease>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(officetelLeaseDataItemReader)
                .processor(officetelLeaseDataItemProcessor)
                .writer(realEstateLeaseItemWriter)
                .build();
    }

}
