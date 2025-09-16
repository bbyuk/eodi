package com.bb.eodi.batch.job.deal.load;

import com.bb.eodi.batch.core.config.EodiBatchProperties;
import com.bb.eodi.batch.job.deal.load.listener.StepSkipListener;
import com.bb.eodi.batch.job.deal.load.tasklet.RealEstateDealApiFetchStepTasklet;
import com.bb.eodi.domain.deal.entity.RealEstateSell;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import com.bb.eodi.infrastructure.api.deal.DealDataApiClient;
import com.bb.eodi.port.out.deal.dto.ApartmentPreSaleRightSellDataItem;
import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
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
 * 월별 부동산 거래 데이터 적재 배치 job 설정
 */
@Configuration
@RequiredArgsConstructor
public class MonthlyDealDataLoadJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final EodiBatchProperties eodiBatchProperties;


    /**
     * 월별 부동산 거래 데이터 적재 batch job
     *
     * @param monthlyDealDataLoadPreprocessStep 월별 부동산 거래 데이터 적재 batch 전처리 step
     * @param apartmentSellApiFetchStep         아파트 매매 데이터 API 요청 step
     * @param apartmentSellDataLoadStep         아파트 매매 데이터 적재 step
     * @return 월별 부동산 거래 데이터 적재 batch job
     */
    @Bean
    public Job monthlyDealDataLoad(
            Step monthlyDealDataLoadPreprocessStep,
            Step apartmentSellApiFetchStep,
            Step apartmentSellDataLoadStep,
            Step apartmentPresaleRightSellApiFetchStep
    ) {
        return new JobBuilder("monthlyDealDataLoad", jobRepository)
                .start(monthlyDealDataLoadPreprocessStep)       // job 전처리
                .next(apartmentSellApiFetchStep)                // 아파트 매매 데이터 API fetch
                .next(apartmentSellDataLoadStep)                // 아파트 매매 데이터 적재
                .next(apartmentPresaleRightSellApiFetchStep)    // 아파트 분양권 매매 데이터 API fetch
                .build();
    }

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
     * 아파트 매매 데이터 API 요청 step tasklet
     *
     * @param legalDongRepository 법정동 repository
     * @param dealDataApiClient   거래 데이터 API client
     * @param objectMapper        object mapper
     * @return 아파트 매매 데이터 API 요청 step tasklet
     */
    @Bean
    @StepScope
    public Tasklet apartmentSellApiFetchStepTasklet(LegalDongRepository legalDongRepository,
                                                    DealDataApiClient dealDataApiClient,
                                                    ObjectMapper objectMapper) {
        return new RealEstateDealApiFetchStepTasklet<>(
                ApartmentSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper
        );
    }


    /**
     * 아파트 매매 데이터 적재 step
     *
     * @param apartmentSellDataItemReader    아파트 매매 데이터 적재 chunk ItemReader
     * @param apartmentSellDataItemProcessor 아파트 매매 대이터 적재 chunk ItemProcessor
     * @param realEstateDealItemWriter       부동산 매매 데이터 적재 chunk ItemWriter
     * @param stepSkipListener               이미 수행된 월 여부 체크 stepSkipListener
     * @return 아파트 매매 데이터 적재 step
     */
    @Bean
    public Step apartmentSellDataLoadStep(
            ItemReader<ApartmentSellDataItem> apartmentSellDataItemReader,
            ItemProcessor<ApartmentSellDataItem, RealEstateSell> apartmentSellDataItemProcessor,
            ItemWriter<RealEstateSell> realEstateDealItemWriter,
            StepSkipListener stepSkipListener
    ) {
        return new StepBuilder("apartmentSellDataLoadStep", jobRepository)
                .<ApartmentSellDataItem, RealEstateSell>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(apartmentSellDataItemReader)
                .processor(apartmentSellDataItemProcessor)
                .writer(realEstateDealItemWriter)
                .listener(stepSkipListener)
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
     * 아파트 분양권 매매 데이터 API 요청 step tasklet
     * @param legalDongRepository 법정동 repository
     * @param dealDataApiClient 부동산 거래 데이터 API Client
     * @param objectMapper objectMapper
     * @return 아파트 분양권 매매 데이터 API 요청 step tasklet
     */
    @Bean
    @StepScope
    public Tasklet apartmentPresaleRightSellApiFetchStepTasklet(
            LegalDongRepository legalDongRepository,
            DealDataApiClient dealDataApiClient,
            ObjectMapper objectMapper
    ) {
        return new RealEstateDealApiFetchStepTasklet<>(
                ApartmentPreSaleRightSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper
        );
    }
}
