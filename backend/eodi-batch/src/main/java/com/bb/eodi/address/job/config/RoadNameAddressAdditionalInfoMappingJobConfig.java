package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import com.bb.eodi.address.job.dto.AdditionalInfoItem;
import com.bb.eodi.address.job.reader.AdditionalInfoItemReader;
import com.bb.eodi.core.EodiBatchProperties;
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
import java.util.Optional;

/**
 * 도로명주소 부가정보 매핑 job config
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RoadNameAddressAdditionalInfoMappingJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;

    private final RoadNameAddressRepository roadNameAddressRepository;
    private final int CONCURRENCY_LIMIT = 10;


    /**
     * 도로명주소 부가정보 매핑 job
     * @param roadNameAddressAdditionalInfoMappingMasterStep 도로명주소 부가정보 매핑 병렬처리 master step
     * @return 도로명주소 부가정보 매핑 job
     */
    @Bean
    public Job roadNameAddressAdditionalInfoMappingJob(Step roadNameAddressAdditionalInfoMappingMasterStep) {
        return new JobBuilder("roadNameAddressAdditionalInfoMappingJob", jobRepository)
                .start(roadNameAddressAdditionalInfoMappingMasterStep)
                .build();
    }

    /**
     * 도로명주소 부가정보 매핑 병렬 partitioner
     * @param targetDirectory jobParameter 대상 경로
     * @return 도로명주소 부가정보 매핑 병렬 partitioner
     */
    @Bean
    @StepScope
    public Partitioner roadNameAddressAdditionalInfoMappingPartitioner(
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        return gridSize -> {
            Map<String, ExecutionContext> partition = new HashMap<>();

            File dir = new File(targetDirectory);

            File[] files = dir.listFiles(file -> file.getName().startsWith("부가정보"));

            for (int i = 0; i < files.length; i++) {
                ExecutionContext context = new ExecutionContext();
                context.put("filePath", files[i].getAbsolutePath());
                partition.put("partition-" + i, context);
            }

            return partition;
        };
    }

    /**
     * 도로명주소 부가정보 매핑 병렬 master step
     * @param roadNameAddressAdditionalInfoMappingPartitioner 도로명주소 부가정보 매핑 병렬 partitioner
     * @param roadNameAddressAdditionalInfoMappingWorkerStep 지분주소 부가정보 매핑 worker step
     * @return 도로명주소 부가정보 매핑 병렬 master step
     */
    @Bean
    public Step roadNameAddressAdditionalInfoMappingMasterStep(
            Partitioner roadNameAddressAdditionalInfoMappingPartitioner,
            Step roadNameAddressAdditionalInfoMappingWorkerStep
    ) {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(CONCURRENCY_LIMIT);

        return new StepBuilder("roadNameAddressAdditionalInfoMappingMasterStep", jobRepository)
                .partitioner("roadNameAddressAdditionalInfoMappingWorkerStep", roadNameAddressAdditionalInfoMappingPartitioner)
                .step(roadNameAddressAdditionalInfoMappingWorkerStep)
                .taskExecutor(taskExecutor)
                .gridSize(CONCURRENCY_LIMIT)
                .build();
    }

    /**
     * 도로명주소 부가정보 매핑 worker step
     * @param additionalInfoItemReader 부가정보 ItemReader
     * @param additionalInfoMappingItemProcessor 부가정보 매핑 ItemProcessor
     * @param roadNameAddressUpdateAdditionalInfoItemWriter 도로명주소 부가정보 update ItemWriter
     * @return
     */
    @Bean
    public Step roadNameAddressAdditionalInfoMappingWorkerStep(
            ItemStreamReader<AdditionalInfoItem> additionalInfoItemReader,
            ItemProcessor<AdditionalInfoItem, RoadNameAddress> additionalInfoMappingItemProcessor,
            ItemWriter<RoadNameAddress> roadNameAddressUpdateAdditionalInfoItemWriter
    ) {
        return new StepBuilder("roadNameAddressAdditionalInfoMappingWorkerStep", jobRepository)
                .<AdditionalInfoItem, RoadNameAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(additionalInfoItemReader)
                .processor(additionalInfoMappingItemProcessor)
                .writer(roadNameAddressUpdateAdditionalInfoItemWriter)
                .stream(additionalInfoItemReader)
                .build();
    }

    /**
     * 부가정보 ItemReader
     * @param filePath stepExecutionContext step별로 read할 파일 경로
     * @return 부가정보 ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<AdditionalInfoItem> additionalInfoItemReader(
            @Value("#{stepExecutionContext['filePath']}") String filePath
    ) {
        return new AdditionalInfoItemReader(filePath);
    }

    /**
     * 도로명주소 부가정보 매핑 ItemProcessor
     * @return 부가정보 ItemProcessor
     */
    @Bean
    @StepScope
    public ItemProcessor<AdditionalInfoItem, RoadNameAddress> additionalInfoMappingItemProcessor() {
        return item -> {
            Optional<RoadNameAddress> result = roadNameAddressRepository.findByManageNo(item.getManageNo());
            if (result.isEmpty()) {
                return null;
            }

            RoadNameAddress roadNameAddress = result.get();
            roadNameAddress.updateBuildingName(item.getBuildingName());

            return roadNameAddress;
        };
    }

    /**
     * 도로명주소 부가정보 매핑 update ItemWriter
     * @return 도로명주소 부가정보 매핑 update ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<RoadNameAddress> roadNameAddressUpdateAdditionalInfoItemWriter() {
        return chunk -> {
            roadNameAddressRepository.batchUpdateAdditionalInfo(chunk.getItems());
        };
    }

}
