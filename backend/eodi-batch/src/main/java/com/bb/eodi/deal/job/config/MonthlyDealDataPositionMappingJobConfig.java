package com.bb.eodi.deal.job.config;

import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * 월별 부동산 실거래가 데이터 좌표 매핑 job config
 */
@Configuration
@RequiredArgsConstructor
public class MonthlyDealDataPositionMappingJobConfig {

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
     * 월별 부동산 실거래가 데이터 좌표 매핑 job
     *
     * @param monthlyRealEstateSellDataPositionMappingFlow  월별 부동산 매매데이터 좌표 매핑 flow
     * @param monthlyRealEstateLeaseDataPositionMappingFlow 월별 부동산 임대차데이터 좌표 매핑 flow
     * @return 월별 부동산 실거래가 데이터 좌료 매핑 job
     */
    @Bean
    public Job monthlyDealDataPositionMappingJob(
            Flow monthlyRealEstateSellDataPositionMappingFlow,
            Flow monthlyRealEstateLeaseDataPositionMappingFlow
    ) {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(2);

        return new JobBuilder("monthlyDealDataPositionMappingJob", jobRepository)
                .start(monthlyRealEstateSellDataPositionMappingFlow)
                .split(executor)
                .add(monthlyRealEstateLeaseDataPositionMappingFlow)
                .end()
                .build();
    }

    /**
     * 월별 부동산 매매 데이터 좌표 매핑 flow
     *
     * @param monthlyRealEstateSellDataPositionMappingItemReader 월별 부동산 매매데이터 좌표 매핑 ItemReader
     * @param realEstateSellDataPositionMappingItemProcessor     부동산 매매데이터 좌표 매핑 ItemProcessor
     * @param realEstateSellItemUpdateWriter                     월별 부동산 매매데이터 좌표 매핑 ItemWriter
     * @param contractDatePartitioner                            계약일 기준 step 병렬 partitioner
     * @return 월별 부동산 매매 데이터 좌표 매핑 flow
     */
    @Bean
    public Flow monthlyRealEstateSellDataPositionMappingFlow(
            ItemReader<RealEstateSell> monthlyRealEstateSellDataPositionMappingItemReader,
            ItemProcessor<RealEstateSell, RealEstateSell> realEstateSellDataPositionMappingItemProcessor,
            ItemWriter<RealEstateSell> realEstateSellItemUpdateWriter,
            Partitioner contractDatePartitioner
    ) {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(LIMIT_CONCURRENCY_LIMIT);

        TaskletStep workerStep = new StepBuilder("monthlyRealEstateSellDataPositionMappingWorkerStep", jobRepository)
                .<RealEstateSell, RealEstateSell>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(monthlyRealEstateSellDataPositionMappingItemReader)
                .processor(realEstateSellDataPositionMappingItemProcessor)
                .writer(realEstateSellItemUpdateWriter)
                .build();

        return new FlowBuilder<Flow>("monthlyRealEstateSellDataPositionMappingFlow")
                .start(new StepBuilder("monthlyRealEstateSellDataPositionMappingMasterStep", jobRepository)
                        .partitioner("monthlyRealEstateSellDataPositionMappingWorkerStep", contractDatePartitioner)
                        .step(workerStep)
                        .gridSize(LIMIT_CONCURRENCY_LIMIT)
                        .taskExecutor(taskExecutor)
                        .build())
                .build();
    }

    /**
     * 월별 부동산 임대차 실거래가 데이터 좌표 매핑 flow
     *
     * @param monthlyRealEstateLeaseDataPositionMappingItemReader 월별 부동산 임대차 실거래가 데이터 좌표 매핑 ItemReader
     * @param realEstateLeaseDataPositionMappingItemProcessor     월별 부동산 임대차 실거래가 데이터 좌표 매핑 ItemProcessor
     * @param realEstateLeaseItemUpdateWriter                     월별 부동산 임대차 실거래가 데이터 좌표 매핑 ItemWriter
     * @param contractDatePartitioner                            계약일 기준 step 병렬 partitioner
     * @return 월별 부동산 임대차 실거래가 데이터 좌표 매핑 flow
     */
    @Bean
    public Flow monthlyRealEstateLeaseDataPositionMappingFlow(
            ItemReader<RealEstateLease> monthlyRealEstateLeaseDataPositionMappingItemReader,
            ItemProcessor<RealEstateLease, RealEstateLease> realEstateLeaseDataPositionMappingItemProcessor,
            ItemWriter<RealEstateLease> realEstateLeaseItemUpdateWriter,
            Partitioner contractDatePartitioner
    ) {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(LIMIT_CONCURRENCY_LIMIT);

        TaskletStep monthlyRealEstateLeaseDataPositionMappingWorkerStep = new StepBuilder("monthlyRealEstateLeaseDataPositionMappingWorkerStep", jobRepository)
                .<RealEstateLease, RealEstateLease>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(monthlyRealEstateLeaseDataPositionMappingItemReader)
                .processor(realEstateLeaseDataPositionMappingItemProcessor)
                .writer(realEstateLeaseItemUpdateWriter)
                .build();

        return new FlowBuilder<Flow>("monthlyRealEstateLeaseDataPositionMappingFlow")
                .start(new StepBuilder("monthlyRealEstateLeaseDataPositionMappingMasterStep", jobRepository)
                        .partitioner("monthlyRealEstateLeaseDataPositionMappingWorkerStep", contractDatePartitioner)
                        .step(monthlyRealEstateLeaseDataPositionMappingWorkerStep)
                        .gridSize(LIMIT_CONCURRENCY_LIMIT)
                        .taskExecutor(taskExecutor)
                        .build())
                .build();
    }


}
