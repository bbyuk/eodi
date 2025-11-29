package com.bb.eodi.address.job.config;

import com.bb.eodi.address.job.reader.LandLotAddressItemReader;
import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.address.job.dto.LandLotAddressItem;
import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.infrastructure.persistence.jdbc.LandLotAddressJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 지번 주소 적재 배치 jobConfig
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class LandLotAddressLoadJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;
    private final LandLotAddressJdbcRepository landLotAddressJdbcRepository;
    private static final int CONCURRENCY_LIMIT = 12;

    /**
     * 지번주소 초기 적재 배치 job
     * @param landLotAddressLoadMasterStep 지번 주소 적재 병렬실행 Master Step
     * @return 지번주소 초기 적재 배치 job
     */
    @Bean
    public Job landLotAddressLoadJob(Step landLotAddressLoadMasterStep) {
        return new JobBuilder("landLotAddressLoadJob", jobRepository)
                .start(landLotAddressLoadMasterStep)
                .build();
    }

    /**
     * 지번주소 초기 적재 배치 Partitioner
     * @param targetDirectory 대상 directory job parameter
     * @return 지번주소 초기 적재 배치 Partitioner
     */
    @Bean
    @StepScope
    public Partitioner landLotAddressPartitioner(
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        return gridSize -> {
            Map<String, ExecutionContext> partition = new HashMap<>();

            File dir = new File(targetDirectory);

            File[] files = dir.listFiles(file -> file.getName().startsWith("jibun"));

            for (int i = 0; i < files.length; i++) {
                ExecutionContext context = new ExecutionContext();
                context.put("filePath", files[i].getAbsolutePath());
                partition.put("partition-" + i, context);
            }

            return partition;
        };
    }

    /**
     * 지번주소 적재 병렬처리 master Step
     * @param landLotAddressPartitioner 지번주소 step partitioner
     * @param landLotAddressLoadWorkerStep 지번주소 적재 worker step
     * @return 지번주소 적재 병렬처리 master Step
     */
    @Bean
    public Step landLotAddressLoadMasterStep(
            Partitioner landLotAddressPartitioner,
            Step landLotAddressLoadWorkerStep
    ) {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(CONCURRENCY_LIMIT);

        return new StepBuilder("landLotAddressLoadMasterStep", jobRepository)
                .partitioner("landLotAddressLoadWorkerStep", landLotAddressPartitioner)
                .step(landLotAddressLoadWorkerStep)
                .gridSize(CONCURRENCY_LIMIT)
                .taskExecutor(executor)
                .build();
    }

    /**
     * 지번주소 적재 worker step
     *
     * @param landLotAddressItemReader              지번주소 ItemReader
     * @param landLotAddressItemProcessor           지번주소 ItemProcessor
     * @param landLotAddressItemWriter              지번주소 ItemWriter
     * @return 지번주소 적재 worker step
     */
    @Bean
    public Step landLotAddressLoadWorkerStep(
            ItemStreamReader<LandLotAddressItem> landLotAddressItemReader,
            ItemProcessor<LandLotAddressItem, LandLotAddress> landLotAddressItemProcessor,
            ItemWriter<LandLotAddress> landLotAddressItemWriter
    ) {
        return new StepBuilder("landLotAddressLoadWorkerStep", jobRepository)
                .<LandLotAddressItem, LandLotAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(landLotAddressItemReader)
                .processor(landLotAddressItemProcessor)
                .writer(landLotAddressItemWriter)
                .stream(landLotAddressItemReader)
                .build();
    }

    /**
     * 지번주소 ItemStreamReader
     * @param filePath ItemStreamReader 대상 파일 경로
     * @return 지번주소 ItemStreamReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<LandLotAddressItem> landLotAddressItemReader(
            @Value("#{stepExecutionContext['filePath']}") String filePath) {
        return new LandLotAddressItemReader(filePath);
    }

    /**
     * 지번주소 ItemProcessor
     *
     * @return 지번주소 ItemProcessor
     */
    @Bean
    @StepScope
    public ItemProcessor<LandLotAddressItem, LandLotAddress> landLotAddressItemProcessor() {
        return landLotAddressItem -> LandLotAddress.builder()
                .legalDongCode(landLotAddressItem.getLegalDongCode())
                .sidoName(landLotAddressItem.getSidoName())
                .sigunguName(landLotAddressItem.getSigunguName())
                .legalUmdName(landLotAddressItem.getLegalUmdName())
                .legalRiName(landLotAddressItem.getLegalRiName())
                .isMountain(landLotAddressItem.getIsMountain())
                .landLotMainNo(Integer.parseInt(Objects.requireNonNull(landLotAddressItem.getLandLotMainNo())))
                .landLotSubNo(Integer.parseInt(Objects.requireNonNull(landLotAddressItem.getLandLotSubNo())))
                .landLotSeq(Long.parseLong(Objects.requireNonNull(landLotAddressItem.getLandLotSeq())))
                .roadNameCode(landLotAddressItem.getRoadNameCode())
                .isUnderground(landLotAddressItem.getIsUnderground())
                .buildingMainNo(Integer.parseInt(Objects.requireNonNull(landLotAddressItem.getLandLotMainNo())))
                .buildingSubNo(Integer.parseInt(Objects.requireNonNull(landLotAddressItem.getBuildingSubNo())))
                .changeReasonCode(landLotAddressItem.getChangeReasonCode())
                .build();
    }

    /**
     * 지번주소 ItemWriter
     *
     * @return 지번주소 ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<LandLotAddress> landLotAddressItemWriter() {
        return landLotAddressChunk ->
                landLotAddressJdbcRepository.saveAll(landLotAddressChunk.getItems());
    }
}
