package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.repository.LandLotAddressRepository;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import com.bb.eodi.address.job.dto.LandLotAddressItem;
import com.bb.eodi.address.job.dto.RoadNameAddressItem;
import com.bb.eodi.address.job.reader.LandLotAddressItemReader;
import com.bb.eodi.address.job.reader.RoadNameAddressItemReader;
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
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 도로명주소 한글 적재 배치 jobConfig
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RoadNameAddressLoadJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;

    private final RoadNameAddressRepository roadNameAddressRepository;
    private final LandLotAddressRepository landLotAddressRepository;

    private static final int CONCURRENCY_LIMIT = 8;

    /**
     * 도로명주소 적재 job
     * 도로명주소 한글 전체분 파일을 일괄 load한다.
     *  - 도로명주소 한글
     *  - 도로명주소 한글 관련지번
     * DB초기 구축시 사용
     *
     * @param roadNameAddressLoadMasterStep 도로명주소 데이터를 로드해 DB에 적재하는 Step
     * @return 도로명주소 적재 job
     */
    @Bean
    public Job roadNameAddressLoadJob(Step roadNameAddressLoadMasterStep, Step landLotAddressLoadMasterStep) {
        return new JobBuilder("roadNameAddressLoadJob", jobRepository)
                .start(roadNameAddressLoadMasterStep)
                .next(landLotAddressLoadMasterStep)
                .build();
    }

    /**
     * 도로명주소 초기 적재 배치 partitioner
     * @param targetDirectory jobParameter -> 대상 directory
     * @return 도로명주소 초기 적재 배치 partitioner
     */
    @Bean
    @StepScope
    public Partitioner roadNameAddressPartitioner(
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ){
        return gridSize -> {
            Map<String, ExecutionContext> partition = new HashMap<>();

            File dir = new File(targetDirectory);
            File[] files = dir.listFiles(file -> file.getName().startsWith("rnaddrkor"));

            for (int i = 0; i < files.length; i++) {
                ExecutionContext context = new ExecutionContext();
                context.put("filePath", files[i].getAbsolutePath());
                partition.put("partition-" + i, context);
            }

            return partition;
        };
    }

    /**
     * 도로명주소 적재 병렬처리 Master step
     * @param roadNameAddressPartitioner 도로명주소 적재 step partitioner
     * @param roadNameAddressLoadWorkerStep 도로명주소 적재 step worker step
     * @return 도로명주소 적재 병렬처리 Master step
     */
    @Bean
    public Step roadNameAddressLoadMasterStep(
            Partitioner roadNameAddressPartitioner,
            Step roadNameAddressLoadWorkerStep
    ) {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(CONCURRENCY_LIMIT);

        return new StepBuilder("roadNameAddressLoadMasterStep", jobRepository)
                .partitioner("roadNameAddressLoadWorkerStep", roadNameAddressPartitioner)
                .step(roadNameAddressLoadWorkerStep)
                .gridSize(CONCURRENCY_LIMIT)
                .taskExecutor(taskExecutor)
                .build();
    }

    /**
     * 도로명주소 적재 worker step
     * @param roadNameAddressItemReader 도로명주소 적재 ItemReader
     * @param roadNameAddressItemProcessor 도로명주소 적재 ItemProcessor
     * @param roadNameAddressItemWriter 도로명주소 적재 ItemWriter
     * @return 도로명주소 적재 worker step
     */
    @Bean
    public Step roadNameAddressLoadWorkerStep(
            ItemStreamReader<RoadNameAddressItem> roadNameAddressItemReader,
            ItemProcessor<RoadNameAddressItem, RoadNameAddress> roadNameAddressItemProcessor,
            ItemWriter<RoadNameAddress> roadNameAddressItemWriter
    ) {
        return new StepBuilder("roadNameAddressLoadWorkerStep", jobRepository)
                .<RoadNameAddressItem, RoadNameAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(roadNameAddressItemReader)
                .processor(roadNameAddressItemProcessor)
                .writer(roadNameAddressItemWriter)
                .stream(roadNameAddressItemReader)
                .build();
    }

    /**
     * 도로명주소 ItemReader
     * @param filePath 대상 파일 경로
     * @return 도로명주소 ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<RoadNameAddressItem> roadNameAddressItemReader(@Value("#{stepExecutionContext['filePath']}") String filePath) {
        return new RoadNameAddressItemReader(filePath);
    }

    /**
     * 도로명주소 ItemWriter
     * @return 도로명주소 ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<RoadNameAddress> roadNameAddressItemWriter() {
        return item -> roadNameAddressRepository.insertBatch(item.getItems());
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
     * 지번주소 ItemWriter
     *
     * @return 지번주소 ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<LandLotAddress> landLotAddressItemWriter() {
        return landLotAddressChunk ->
                landLotAddressRepository.insertBatch(landLotAddressChunk.getItems());
    }

}
