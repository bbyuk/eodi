package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.dto.AddressPositionMappingParameter;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import com.bb.eodi.address.domain.util.GeoToolsBigDecimalConverter;
import com.bb.eodi.address.job.dto.AddressPositionItem;
import com.bb.eodi.address.job.reader.AddressPositionAllItemReader;
import com.bb.eodi.core.EodiBatchProperties;
import lombok.RequiredArgsConstructor;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 도로명주소 주소위치정보 전체분 매핑 배치 job config
 */
@Configuration
@RequiredArgsConstructor
public class RoadNameAddressPositionMappingJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EodiBatchProperties eodiBatchProperties;
    private final RoadNameAddressRepository roadNameAddressRepository;

    private static final int CONCURRENCY_LIMIT = 6;

    /**
     * 주소DB - 위치요약정보DB 전체분 도로명주소 매핑 job
     * @param roadNameAddressPositionMappingMasterStep 위치요약정보 데이터 병렬 적재 master step
     * @return 도로명주소DB - 위치요약정보DB 전체분 데이터 초기적재 job
     */
    @Bean
    public Job roadNameAddressPositionMappingJob(
            Step roadNameAddressPositionMappingMasterStep
    ) {
        return new JobBuilder("roadNameAddressPositionMappingJob", jobRepository)
                .start(roadNameAddressPositionMappingMasterStep)
                .build();
    }

    /**
     * 도로명주소 위치요약정보 데이터 매핑 병렬 partitioner
     * @param targetDirectory 대상 디렉터리 - job parameter
     * @return 도로명주소 위치요약정보 데이터 매핑 병렬 partitioner
     */
    @Bean
    @StepScope
    public Partitioner roadNameAddressPositionMappingPartitioner(
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        return gridSize -> {
            Map<String, ExecutionContext> partition = new HashMap<>();

            File dir = new File(targetDirectory);

            File[] files = dir.listFiles(file -> file.getName().startsWith("entrc"));

            for (int i = 0; i < files.length; i++) {
                ExecutionContext context = new ExecutionContext();
                context.put("filePath", files[i].getAbsolutePath());
                partition.put("partition-" + i, context);
            }

            return partition;
        };
    }

    /**
     * 도로명주소 주소 위치정보 매핑 병렬 처리 master step
     * @param roadNameAddressPositionMappingPartitioner 도로명주소 주소 위치정보 매핑 병렬 partitioner
     * @param roadNameAddressPositionMappingWorkerStep 도로명주소 주소 위치정보 매핑 병렬 처리 worker step
     * @return 주소위치정보 전체분 병렬 적재 master step
     */
    @Bean
    public Step roadNameAddressPositionMappingMasterStep(
            Partitioner roadNameAddressPositionMappingPartitioner,
            Step roadNameAddressPositionMappingWorkerStep
    ) {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(CONCURRENCY_LIMIT);

        return new StepBuilder("roadNameAddressPositionMappingMasterStep", jobRepository)
                .partitioner("roadNameAddressPositionMappingWorkerStep", roadNameAddressPositionMappingPartitioner)
                .step(roadNameAddressPositionMappingWorkerStep)
                .taskExecutor(executor)
                .gridSize(CONCURRENCY_LIMIT)
                .build();
    }

    /**
     * 도로명주소 주소 위치정보 매핑 병렬처리 worker step
     * @param addressPositionAllItemReader 주소위치정보 전체분 ItemReader
     * @param roadNameAddressPositionMappingItemProcessor 도로명주소 주소 위치정보 매핑 ItemProcessor
     * @param roadNameAddressPositionMappingItemWriter 도로명주소 주소 위치정보 매핑 ItemWriter
     * @return 주소위치정보 전체분 병렬 적재 worker step
     */
    @Bean
    public Step roadNameAddressPositionMappingWorkerStep(
            ItemStreamReader<AddressPositionItem> addressPositionAllItemReader,
            ItemProcessor<AddressPositionItem, AddressPositionMappingParameter> roadNameAddressPositionMappingItemProcessor,
            ItemWriter<AddressPositionMappingParameter> roadNameAddressPositionMappingItemWriter
    ) {
        return new StepBuilder("addressPositionLoadWorkerStep", jobRepository)
                .<AddressPositionItem, AddressPositionMappingParameter>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(addressPositionAllItemReader)
                .processor(roadNameAddressPositionMappingItemProcessor)
                .writer(roadNameAddressPositionMappingItemWriter)
                .stream(addressPositionAllItemReader)
                .build();
    }

    /**
     * 주소위치정보 전체분 ItemStreamReader
     * @param filePath 대상 파티션 파일 절대경로
     * @return 주소위치정보 전체분 ItemStreamReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<AddressPositionItem> addressPositionAllItemReader(
            @Value("#{stepExecutionContext['filePath']}") String filePath
    ) {
        return new AddressPositionAllItemReader(filePath);
    }

    /**
     * 도로명주소 주소 위치정보 매핑 ItemProcessor
     * @return 도로명주소 주소 위치정보 매핑 ItemProcessor
     */
    @Bean
    @StepScope
    public ItemProcessor<AddressPositionItem, AddressPositionMappingParameter> roadNameAddressPositionMappingItemProcessor() {
        Function<String, BigDecimal> parseBigDecimalWithNull = (str) -> !StringUtils.hasText(str) ? null : new BigDecimal(str);

        return item -> {
            if (!StringUtils.hasText(item.getXPos()) || !StringUtils.hasText(item.getYPos())) {
                return null;
            }

            BigDecimal[] wgs84 = GeoToolsBigDecimalConverter.toWgs84(
                    parseBigDecimalWithNull.apply(item.getXPos()),
                    parseBigDecimalWithNull.apply(item.getYPos())
            );


            return AddressPositionMappingParameter.builder()
                    .legalDongCode(item.getLegalDongCode())
                    .roadNameCode(item.getRoadNameCode())
                    .buildingMainNo(Integer.parseInt(item.getBuildingMainNo()))
                    .buildingSubNo(Integer.parseInt(item.getBuildingSubNo()))
                    .isUnderground(item.getIsUnderground())
                    .xPos(wgs84[0])
                    .yPos(wgs84[1])
                    .build();
        };
    }

    /**
     * 주소위치정보 ItemWriter
     * @return 주소위치정보 ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<AddressPositionMappingParameter> addressPositionItemWriter() {
        return chunk -> roadNameAddressRepository.batchUpdatePosition(chunk.getItems());
    }
}
