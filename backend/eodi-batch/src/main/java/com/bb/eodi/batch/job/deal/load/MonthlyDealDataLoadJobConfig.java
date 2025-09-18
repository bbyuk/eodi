package com.bb.eodi.batch.job.deal.load;

import com.bb.eodi.batch.core.config.EodiBatchProperties;
import com.bb.eodi.batch.core.repository.BatchMetaRepository;
import com.bb.eodi.batch.job.deal.load.decider.FlowSkipDecider;
import com.bb.eodi.batch.job.deal.load.reader.RealEstateDealDataItemStreamReader;
import com.bb.eodi.batch.job.deal.load.tasklet.RealEstateDealApiFetchStepTasklet;
import com.bb.eodi.domain.deal.entity.RealEstateSell;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import com.bb.eodi.infrastructure.api.deal.DealDataApiClient;
import com.bb.eodi.port.out.deal.dto.ApartmentPresaleRightSellDataItem;
import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
import com.bb.eodi.port.out.deal.dto.MultiUnitDetachedSellDataItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.file.Paths;

import static com.bb.eodi.batch.core.enums.BatchExecutionStatus.COMPLETED;
import static com.bb.eodi.batch.core.enums.BatchExecutionStatus.CONTINUE;

/**
 * 월별 부동산 거래 데이터 적재 배치 job 설정
 */
@Configuration
@RequiredArgsConstructor
public class MonthlyDealDataLoadJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final BatchMetaRepository batchMetaRepository;

    private final EodiBatchProperties eodiBatchProperties;

    /**
     * 월별 부동산 거래 데이터 적재 batch job
     *
     * @param monthlyDealDataLoadPreprocessFlow         월별 부동산 거래 데이터 적재 batch 전처리 step
     * @param apartmentSellDataLoadFlow                 아파트 매매 데이터 적재 flow
     * @param apartmentPresaleRightSellDataLoadFlow     아파트 분양권 매매 데이터 적재 flow
     * @return 월별 부동산 거래 데이터 적재 batch job
     */
    @Bean
    public Job monthlyDealDataLoad(
            Flow monthlyDealDataLoadPreprocessFlow,
            Flow apartmentSellDataLoadFlow,
            Flow apartmentPresaleRightSellDataLoadFlow,
            Flow multiUnitDetachedSellDataLoadFlow
    ) {

        return new JobBuilder("monthlyDealDataLoad", jobRepository)
                .start(monthlyDealDataLoadPreprocessFlow)       // job 전처리
                .next(apartmentSellDataLoadFlow)                // 아파트 매매
                .next(apartmentPresaleRightSellDataLoadFlow)    // 아파트 분양권 매매
                .next(multiUnitDetachedSellDataLoadFlow)        // 단독/다가구주택 매매
                .end()
                .build();
    }


    /**
     * 월별 부동산 거래 데이터 적재 배치 전처리 flow
     *
     * @param monthlyDealDataLoadPreprocessStep 월별 부동산 거래 데이터 적재 배치 전처리 step
     * @return 월별 부동산 거래 데이터 적재 배치 전처리 flow
     */
    @Bean
    public Flow monthlyDealDataLoadPreprocessFlow(Step monthlyDealDataLoadPreprocessStep) {
        String flowName = "monthlyDealDataLoadPreprocessFlow";
        return new FlowBuilder<Flow>(flowName)
                .start(monthlyDealDataLoadPreprocessStep)
                .end();
    }

    /**
     * 아파트 매매 데이터 적재 flow
     * @param apartmentSellApiFetchStep 아파트 매매 데이터 API 요청 step
     * @param apartmentSellDataLoadStep 아파트 매매 데이터 적재 step
     * @return 아파트 매매 데이터 적재 flow
     */
    @Bean
    public Flow apartmentSellDataLoadFlow(
            Step apartmentSellApiFetchStep,
            Step apartmentSellDataLoadStep) {
        String flowName = "apartmentSellDataLoadFlow";
        FlowSkipDecider flowSkipDecider = new FlowSkipDecider(flowName, batchMetaRepository);

        return new FlowBuilder<Flow>(flowName)
                .start(flowSkipDecider)
                    .on(CONTINUE.name())
                        .to(apartmentSellApiFetchStep)
                        .next(apartmentSellDataLoadStep)
                .from(flowSkipDecider)
                    .on(COMPLETED.name())
                        .end()
                .end();
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
                .build();
    }

    /**
     * 아파트 분양권 매매 데이터 적재 flow
     *
     * @param apartmentPresaleRightSellApiFetchStep 아파트 분양권 매매 데이터 API 요청 step
     * @return 아파트 분양권 매매 데이터 적재 flow
     */
    @Bean
    public Flow apartmentPresaleRightSellDataLoadFlow(
            Step apartmentPresaleRightSellApiFetchStep,
            Step apartmentPresaleRightSellDataLoadStep
    ) {
        String flowName = "apartmentPresaleRightSellDataLoadFlow";
        FlowSkipDecider flowSkipDecider = new FlowSkipDecider(flowName, batchMetaRepository);
        return new FlowBuilder<Flow>(flowName)
                .start(flowSkipDecider)
                    .on(CONTINUE.name())
                        .to(apartmentPresaleRightSellApiFetchStep)
                        .next(apartmentPresaleRightSellDataLoadStep)
                .from(flowSkipDecider)
                    .on(COMPLETED.name())
                        .end()
                .end();
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
     *
     * @param legalDongRepository 법정동 repository
     * @param dealDataApiClient   부동산 거래 데이터 API Client
     * @param objectMapper        objectMapper
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
                ApartmentPresaleRightSellDataItem.class,
                legalDongRepository,
                dealDataApiClient,
                objectMapper
        );
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
                .build();
    }

    /**
     * 단독/다가구주택 매매 데이터 적재 flow
     * @param multiUnitDetachedSellApiFetchStep 단독/다가구주택 매매 데이터 API 요청 step
     * @param multiUnitDetachedSellDataLoadStep 단독/다가구주택 매매 데이터 적재 step
     * @return 단독/다가구주택 매매 데이터 적재 flow
     */
    @Bean
    public Flow multiUnitDetachedSellDataLoadFlow(
            Step multiUnitDetachedSellApiFetchStep,
            Step multiUnitDetachedSellDataLoadStep
    ) {
        String flowName = "multiUnitDetachedSellDataLoadFlow";
        FlowSkipDecider skipDecider = new FlowSkipDecider(flowName, batchMetaRepository);

        return new FlowBuilder<Flow>(flowName)
                .start(skipDecider)
                    .on(CONTINUE.name())
                        .to(multiUnitDetachedSellApiFetchStep)
                        .next(multiUnitDetachedSellDataLoadStep)
                .from(skipDecider)
                    .on(COMPLETED.name())
                        .end()
                .build();
    }

    /**
     * 단독/다가구주택 매매 데이터 API 요청 step
     * @param legalDongRepository 법정동 repository
     * @param dealDataApiClient 부동산 실거래가 데이터 API client
     * @param objectMapper objectMapper
     * @return 단독/다가구주택 매매 데이터 API 요청 step
     */
    @Bean
    public Step multiUnitDetachedSellApiFetchStep(
            LegalDongRepository legalDongRepository,
            DealDataApiClient dealDataApiClient,
            ObjectMapper objectMapper
    ) {
        return new StepBuilder("multiUnitDetachedSellApiFetchStep", jobRepository)
                .tasklet(new RealEstateDealApiFetchStepTasklet<>(
                        MultiUnitDetachedSellDataItem.class,
                        legalDongRepository,
                        dealDataApiClient,
                        objectMapper
                ), transactionManager)
                .build();
    }

    /**
     * 단독/다가구주택 매매 데이터 적재 step
     * @param multiUnitDetachedSellDataItemReader 단독/다가구주택 매매 데이터 chunk ItemReader
     * @param multiUnitDetachedSellDataItemProcessor 단독/다가구주택 매매 데이터 chunk ItemProcessor
     * @param realEstateSellItemWriter 부동산 매매 데이터 chunk ItemWriter
     * @return
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
                .build();
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
