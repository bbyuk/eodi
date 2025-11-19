package com.bb.eodi.batch.job.address.config;

import com.bb.eodi.batch.core.config.EodiBatchProperties;
import com.bb.eodi.batch.job.address.dto.AddressPositionItem;
import com.bb.eodi.batch.job.address.entity.AddressPosition;
import com.bb.eodi.batch.job.address.reader.AddressPositionAllItemReader;
import com.bb.eodi.batch.job.address.repository.AddressPositionRepository;
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
 * 주소 위치정보 전체분 초기 적재 배치 job config
 */
@Configuration
@RequiredArgsConstructor
public class AddressPositionLoadJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EodiBatchProperties eodiBatchProperties;
    private final AddressPositionRepository addressPositionRepository;

    private static final int CONCURRENCY_LIMIT = 12;

    /**
     * 도로명주소DB - 위치요약정보DB 전체분 데이터 초기 적재 job
     * @param addressPositionLoadMasterStep 위치요약정보 데이터 병렬 적재 master step
     * @return 도로명주소DB - 위치요약정보DB 전체분 데이터 초기적재 job
     */
    @Bean
    public Job addressPositionLoadJob(
            Step addressPositionLoadMasterStep
    ) {
        return new JobBuilder("addressPositionLoadJob", jobRepository)
                .start(addressPositionLoadMasterStep)
                .build();
    }

    /**
     * 주소상세위치 데이터 병렬 적재 partitioner
     * @param targetDirectory 대상 디렉터리 - job parameter
     * @return 주소상세위치 데이터 병렬 적재 partitioner
     */
    @Bean
    @StepScope
    public Partitioner addressPositionLoadPartitioner(
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
     * 주소위치정보 전체분 병렬 적재 master step
     * @param addressPositionLoadPartitioner 주소위치정보 전체분 병렬 적재 partitioner
     * @param addressPositionLoadWorkerStep 주소위치정보 전체분 병렬 적재 worker step
     * @return 주소위치정보 전체분 병렬 적재 master step
     */
    @Bean
    public Step addressPositionLoadMasterStep(
            Partitioner addressPositionLoadPartitioner,
            Step addressPositionLoadWorkerStep
    ) {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(CONCURRENCY_LIMIT);

        return new StepBuilder("addressPositionLoadMasterStep", jobRepository)
                .partitioner("addressPositionLoadWorkerStep", addressPositionLoadPartitioner)
                .step(addressPositionLoadWorkerStep)
                .taskExecutor(executor)
                .gridSize(CONCURRENCY_LIMIT)
                .build();
    }

    /**
     * 주소위치정보 전체분 병렬 적재 worker step
     * @param addressPositionAllItemReader 주소위치정보 전체분 ItemReader
     * @param addressPositionItemProcessor 주소위치정보 ItemProcessor
     * @param addressPositionItemWriter 주소위치정보 ItemWriter
     * @return 주소위치정보 전체분 병렬 적재 worker step
     */
    @Bean
    public Step addressPositionLoadWorkerStep(
            ItemStreamReader<AddressPositionItem> addressPositionAllItemReader,
            ItemProcessor<AddressPositionItem, AddressPosition> addressPositionItemProcessor,
            ItemWriter<AddressPosition> addressPositionItemWriter
    ) {
        return new StepBuilder("addressPositionLoadWorkerStep", jobRepository)
                .<AddressPositionItem, AddressPosition>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(addressPositionAllItemReader)
                .processor(addressPositionItemProcessor)
                .writer(addressPositionItemWriter)
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
     * 주소위치정보 전체분 ItemProcessor
     * @return 주소위치정보 전체분 ItemProcessor
     */
    @Bean
    @StepScope
    public ItemProcessor<AddressPositionItem, AddressPosition> addressPositionItemProcessor() {
        Function<String, Integer> parseIntWithNull = (str) -> str == null || !StringUtils.hasText(str) ? null : Integer.parseInt(str);
        Function<String, BigDecimal> parseBigDecimalWithNull = (str) -> str == null || !StringUtils.hasText(str) ? null : new BigDecimal(str);

        return item -> AddressPosition.builder()
                .sigunguCode(item.getSigunguCode())
                .entranceSeq(item.getEntranceSeq())
                .legalDongCode(item.getLegalDongCode())
                .sidoName(item.getSidoName())
                .sigunguName(item.getSigunguName())
                .umdName(item.getUmdName())
                .roadName(item.getRoadName())
                .isUnderground(item.getIsUnderground())
                .buildingMainNo(parseIntWithNull.apply(item.getBuildingMainNo()))
                .buildingSubNo(parseIntWithNull.apply(item.getBuildingSubNo()))
                .buildingName(item.getBuildingName())
                .zipNo(item.getZipNo())
                .buildingType(item.getBuildingType())
                .isBuildingGroup(item.getIsBuildingGroup())
                .xPos(parseBigDecimalWithNull.apply(item.getXPos()))
                .yPos(parseBigDecimalWithNull.apply(item.getYPos()))
                .build();
    }

    /**
     * 주소위치정보 ItemWriter
     * @return 주소위치정보 ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<AddressPosition> addressPositionItemWriter() {
        return chunk -> addressPositionRepository.saveAll(chunk.getItems());
    }
}
