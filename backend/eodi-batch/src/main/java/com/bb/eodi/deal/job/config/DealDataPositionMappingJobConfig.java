package com.bb.eodi.deal.job.config;

import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static com.bb.eodi.deal.job.config.DealJobContextKey.*;


/**
 * 월별 부동산 실거래가 데이터 좌표 매핑 job config
 */
@Configuration
@RequiredArgsConstructor
public class DealDataPositionMappingJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;

    private final static int LIMIT_CONCURRENCY_LIMIT = 4;


    /**
     * 1. 부동산 실거래가 데이터를 월별로 read
     * 병렬처리
     * - 매매
     * - 임대차
     * 2. Processor에서 매핑 처리
     * 2.1. 부동산 실거래가 테이블.지번 -> 건물주소 테이블.지번 매핑 -> 좌표테이블.좌표 -> 부동산 실거래가 테이블.지번에 매핑
     * 3. write
     */

    /**
     * 월별 부동산 실거래가 데이터 좌표 매핑 Flow
     *
     * @param realEstateSellDataPositionMappingFlow  월별 부동산 매매데이터 좌표 매핑 flow
     * @param realEstateLeaseDataPositionMappingFlow 월별 부동산 임대차데이터 좌표 매핑 flow
     * @return 월별 부동산 실거래가 데이터 좌료 매핑 job
     */
    @Bean
    public Flow dealDataPositionMappingFlow(
            Step dealDataPositionMappingFlowPreprocessStep,
            Flow realEstateSellDataPositionMappingFlow,
            Flow realEstateLeaseDataPositionMappingFlow
    ) {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(2);

        return new FlowBuilder<SimpleFlow>("dealDataPositionMappingFlow")
                .start(dealDataPositionMappingFlowPreprocessStep)
                .split(executor)
                .add(
                        realEstateSellDataPositionMappingFlow,
                        realEstateLeaseDataPositionMappingFlow
                )
                .end();
    }

    /**
     * 부동산 실거래가 데이터 위치정보 매핑 flow 전처리 step
     *
     * @return
     */
    @Bean
    public Step dealDataPositionMappingFlowPreprocessStep() {
        return new StepBuilder("dealDataPositionMappingFlowPreprocessStep", jobRepository)
                .tasklet(((contribution, chunkContext) -> {
                    ExecutionContext jobCtx =
                            contribution.getStepExecution()
                                    .getJobExecution()
                                    .getExecutionContext();

                    Set<String> updatedSellYearMonth = (Set<String>) jobCtx.get(UPDATED_SELL_YEAR_MONTH.name());
                    Set<String> updatedLeaseYearMonth = (Set<String>) jobCtx.get(UPDATED_LEASE_YEAR_MONTH.name());

                    jobCtx.put(TARGET_SELL_YEAR_MONTH.name(), new ArrayList(updatedSellYearMonth));
                    jobCtx.put(TARGET_SELL_YEAR_MONTH_IDX.name(), 0);

                    jobCtx.put(TARGET_LEASE_YEAR_MONTH.name(), new ArrayList(updatedLeaseYearMonth));
                    jobCtx.put(TARGET_LEASE_YEAR_MONTH_IDX.name(), 0);

                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
    }

    /**
     * 부동산 매매 데이터 좌표 매핑 flow
     *
     * @param monthlyRealEstateSellDataPositionMappingItemReader 월별 부동산 매매데이터 좌표 매핑 ItemReader
     * @param realEstateSellDataPositionMappingItemProcessor     부동산 매매데이터 좌표 매핑 ItemProcessor
     * @param realEstateSellItemUpdateWriter                     월별 부동산 매매데이터 좌표 매핑 ItemWriter
     * @param sellContractDatePartitioner                        계약일 기준 step 병렬 partitioner
     * @return 월별 부동산 매매 데이터 좌표 매핑 flow
     */
    @Bean
    public Flow realEstateSellDataPositionMappingFlow(
            ItemReader<RealEstateSell> monthlyRealEstateSellDataPositionMappingItemReader,
            ItemProcessor<RealEstateSell, RealEstateSell> realEstateSellDataPositionMappingItemProcessor,
            ItemWriter<RealEstateSell> realEstateSellItemUpdateWriter,
            Partitioner sellContractDatePartitioner,
            JobExecutionDecider sellPositionMappingFlowDecider
    ) {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(LIMIT_CONCURRENCY_LIMIT);

        Step workerStep = new StepBuilder("realEstateSellDataPositionMappingWorkerStep", jobRepository)
                .<RealEstateSell, RealEstateSell>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(monthlyRealEstateSellDataPositionMappingItemReader)
                .processor(realEstateSellDataPositionMappingItemProcessor)
                .writer(realEstateSellItemUpdateWriter)
                .build();

        Step masterStep = new StepBuilder("realEstateSellDataPositionMappingMasterStep", jobRepository)
                .partitioner("monthlyRealEstateSellDataPositionMappingWorkerStep", sellContractDatePartitioner)
                .step(workerStep)
                .gridSize(LIMIT_CONCURRENCY_LIMIT)
                .taskExecutor(taskExecutor)
                .build();

        return new FlowBuilder<Flow>("realEstateSellDataPositionMappingFlow")
                .start(sellPositionMappingFlowDecider)
                .on("CONTINUE").to(masterStep)
                .from(masterStep)
                .on("*").to(sellPositionMappingFlowDecider)
                .from(sellPositionMappingFlowDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 부동산 임대차 실거래가 데이터 좌표 매핑 flow
     *
     * @param monthlyRealEstateLeaseDataPositionMappingItemReader 월별 부동산 임대차 실거래가 데이터 좌표 매핑 ItemReader
     * @param realEstateLeaseDataPositionMappingItemProcessor     월별 부동산 임대차 실거래가 데이터 좌표 매핑 ItemProcessor
     * @param realEstateLeaseItemUpdateWriter                     월별 부동산 임대차 실거래가 데이터 좌표 매핑 ItemWriter
     * @param leaseContractDatePartitioner                        계약일 기준 step 병렬 partitioner
     * @param leasePositionMappingFlowDecider                     임대차 데이터 위치정보 매핑 FlowDecider
     * @return 월별 부동산 임대차 실거래가 데이터 좌표 매핑 flow
     */
    @Bean
    @JobScope
    public Flow realEstateLeaseDataPositionMappingFlow(
            ItemReader<RealEstateLease> monthlyRealEstateLeaseDataPositionMappingItemReader,
            ItemProcessor<RealEstateLease, RealEstateLease> realEstateLeaseDataPositionMappingItemProcessor,
            ItemWriter<RealEstateLease> realEstateLeaseItemUpdateWriter,
            Partitioner leaseContractDatePartitioner,
            JobExecutionDecider leasePositionMappingFlowDecider) {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(LIMIT_CONCURRENCY_LIMIT);


        Step workerStep = new StepBuilder("realEstateLeaseDataPositionMappingWorkerStep", jobRepository)
                .<RealEstateLease, RealEstateLease>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(monthlyRealEstateLeaseDataPositionMappingItemReader)
                .processor(realEstateLeaseDataPositionMappingItemProcessor)
                .writer(realEstateLeaseItemUpdateWriter)
                .build();

        Step masterStep = new StepBuilder("realEstateLeaseDataPositionMappingMasterStep", jobRepository)
                .partitioner("realEstateLeaseDataPositionMappingWorkerStep", leaseContractDatePartitioner)
                .step(workerStep)
                .gridSize(LIMIT_CONCURRENCY_LIMIT)
                .taskExecutor(taskExecutor)
                .build();

        return new FlowBuilder<Flow>("realEstateLeaseDataPositionMappingFlow")
                .start(leasePositionMappingFlowDecider)
                .on("CONTINUE").to(masterStep)
                .from(masterStep)
                .on("*").to(leasePositionMappingFlowDecider)
                .from(leasePositionMappingFlowDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }


}
