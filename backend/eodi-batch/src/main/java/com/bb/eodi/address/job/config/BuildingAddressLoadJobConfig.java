package com.bb.eodi.address.job.config;

import com.bb.eodi.address.job.reader.BuildingAddressItemReader;
import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.address.domain.dto.BuildingAddressItem;
import com.bb.eodi.address.domain.entity.BuildingAddress;
import com.bb.eodi.address.domain.repository.BuildingAddressJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 건물주소 테이블 초기 적재 job config
 */
@Configuration
@RequiredArgsConstructor
public class BuildingAddressLoadJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;
    private final BuildingAddressJdbcRepository buildingAddressRepository;

    private static final int CONCURRENCY_LIMIT = 12;

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(CONCURRENCY_LIMIT);
        return executor;
    }

    /**
     * 건물DB - 건물주소 데이터 초기 적재 job
     *
     * @param buildingAddressLoadMasterStep 건물주소 데이터 병렬 적재 master step
     * @return 건물주소 데이터 초기 적재 job
     */
    @Bean
    public Job buildingAddressLoadJob(Step buildingAddressLoadMasterStep) {
        return new JobBuilder("buildingAddressLoadJob", jobRepository)
                .start(buildingAddressLoadMasterStep)
                .build();
    }

    @Bean
    public Step buildingAddressLoadMasterStep(
            Partitioner buildingAddressPartitioner,
            Step buildingAddressLoadWorkerStep
    ) {
        return new StepBuilder("buildingAddressLoadMasterStep", jobRepository)
                .partitioner("buildingAddressLoadWorkerStep", buildingAddressPartitioner)
                .step(buildingAddressLoadWorkerStep)
                .taskExecutor(taskExecutor())
                .gridSize(CONCURRENCY_LIMIT)
                .build();
    }

    /**
     * 건물주소 초기 적재 worker step
     * @param buildingAddressItemReader 건물주소 ItemReader
     * @param buildingAddressItemProcessor 건물주소 ItemProcessor
     * @param buildingAddressItemWriter 건물주소 ItemWriter
     * @return 건물주소 초기 적재 worker step
     */
    @Bean
    public Step buildingAddressLoadWorkerStep(
            ItemStreamReader<BuildingAddressItem> buildingAddressItemReader,
            ItemProcessor<BuildingAddressItem, BuildingAddress> buildingAddressItemProcessor,
            ItemWriter<BuildingAddress> buildingAddressItemWriter
    ) {
        return new StepBuilder("buildingAddressLoadWorkerStep", jobRepository)
                .<BuildingAddressItem, BuildingAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(buildingAddressItemReader)
                .processor(buildingAddressItemProcessor)
                .writer(buildingAddressItemWriter)
                .stream(buildingAddressItemReader)
                .build();
    }


    /**
     * 건물주소 파일단위 partitioner
     * @param targetDirectory
     * @return
     */
    @Bean
    @StepScope
    public Partitioner buildingAddressPartitioner(
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        return gridSize -> {
            Map<String, ExecutionContext> partitions = new HashMap<>();
            File dir = new File(targetDirectory);

            File[] files = dir.listFiles(file -> file.getName().startsWith("build"));

            int idx = 0;

            for (File file : files) {
                ExecutionContext context = new ExecutionContext();
                context.putString("filePath", file.getAbsolutePath());
                partitions.put("partition-" + idx++, context);
            }

            return partitions;
        };
    }

    /**
     * 건물 주소 ItemReader Resource단위 reader
     * @param filePath stepExecutionContext value로 read할 대상 파일
     * @return 건물 주소 ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<BuildingAddressItem> buildingAddressItemReader(
            @Value("#{stepExecutionContext['filePath']}") String filePath
    ) {
        return new BuildingAddressItemReader(filePath);
    }


    /**
     * 건물주소 전체분 ItemProcessor
     *
     * @return 건물주소 전체분 ItemProcessor
     */
    @Bean
    @StepScope
    public ItemProcessor<BuildingAddressItem, BuildingAddress> buildingAddressItemProcessor() {
        Function<String, Integer> parseIntWithNull = str -> str == null || !StringUtils.hasText(str) ? null : Integer.parseInt(str);

        return item -> BuildingAddress.builder()
                .legalDongCode(item.getLegalDongCode())
                .sidoName(item.getSidoName())
                .sigunguName(item.getSigunguName())
                .legalUmdName(item.getLegalUmdName())
                .legalRiName(item.getLegalUmdName())
                .isMountain(item.getIsMountain())
                .landLotMainNo(parseIntWithNull.apply(item.getLandLotMainNo()))
                .landLotSubNo(parseIntWithNull.apply(item.getLandLotSubNo()))
                .roadNameCode(item.getRoadNameCode())
                .roadName(item.getRoadName())
                .isUnderground(item.getIsUnderground())
                .buildingMainNo(parseIntWithNull.apply(item.getBuildingMainNo()))
                .buildingSubNo(parseIntWithNull.apply(item.getBuildingSubNo()))
                .buildingName(item.getBuildingName())
                .buildingNameDetail(item.getBuildingNameDetail())
                .buildingManageNo(item.getBuildingManageNo())
                .umdSeq(item.getUmdSeq())
                .admDongCode(item.getAdmDongCode())
                .admDongName(item.getAdmDongName())
                .zipNo(item.getZipNo())
                .zipNoSeq(item.getZipNoSeq())
                .changeReasonCode(item.getChangeReasonCode())
                .announcementDate(item.getAnnouncementDate())
                .sigunguBuildingName(item.getSigunguBuildingName())
                .isComplex(item.getIsComplex())
                .basicDistrictNo(item.getBasicDistrictNo())
                .hasDetailAddress(item.getHasDetailAddress())
                .remark1(item.getRemark1())
                .remark2(item.getRemark2())
                .build();
    }

    /**
     * 건물주소 초기 적재 배치 itemWriter
     * @return 건물주소 초기 적재 배치 itemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<BuildingAddress> buildingAddressItemWriter() {
        return chunk -> buildingAddressRepository.saveAll(chunk.getItems());
    }


}
