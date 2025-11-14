package com.bb.eodi.batch.job.address.config;

import com.bb.eodi.batch.core.config.EodiBatchProperties;
import com.bb.eodi.batch.job.address.dto.LandLotAddressItem;
import com.bb.eodi.batch.job.address.dto.RoadNameAddressItem;
import com.bb.eodi.batch.job.address.entity.LandLotAddress;
import com.bb.eodi.batch.job.address.entity.RoadNameAddress;
import com.bb.eodi.batch.job.address.reader.LandLotAddressItemReader;
import com.bb.eodi.batch.job.address.repository.LandLotAddressJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.util.Arrays;
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
    private final LandLotAddressJpaRepository landLotAddressJpaRepository;

    @Bean
    public Job landLotAddressLoadJob(Step landLotAddressLoadStep) {
        return new JobBuilder("landLotAddressLoadJob", jobRepository)
                .start(landLotAddressLoadStep)
                .build();
    }

    /**
     * 지번주소 적재 step
     * @param multiResourceLandLotAddressItemReader 지번주소 MultiResourceAwareItemReader
     * @param landLotAddressItemProcessor 지번주소 ItemProcessor
     * @param landLotAddressItemWriter 지번주소 ItemWriter
     * @return
     */
    @Bean
    public Step landLotAddressLoadStep(
            ItemReader<LandLotAddressItem> multiResourceLandLotAddressItemReader,
            ItemProcessor<LandLotAddressItem, LandLotAddress> landLotAddressItemProcessor,
            ItemWriter<LandLotAddress> landLotAddressItemWriter
    ) {
        return new StepBuilder("roadNameAddressLoadStep", jobRepository)
                .<LandLotAddressItem, LandLotAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(multiResourceLandLotAddressItemReader)
                .processor(landLotAddressItemProcessor)
                .writer(landLotAddressItemWriter)
                .build();
    }

    /**
     * 지번주소 MultiResourceAwareItemReader
     * @param targetDirectoryPath 대상 디렉터리 path - job parameter
     * @param itemReader 아이템 reader 구현체
     * @return 지번주소 MultiResourceAwareItemReader
     */
    @Bean
    public MultiResourceItemReader<LandLotAddressItem> multiResourceLandLotAddressItemReader(
            @Value("#{jobParameters['target-directory']}") String targetDirectoryPath,
            LandLotAddressItemReader itemReader
    ) {
        MultiResourceItemReader<LandLotAddressItem> reader = new MultiResourceItemReader<>();

        File directory = new File(targetDirectoryPath);
        Resource[] landLotAddressFileResources = Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                .filter(file -> file.getName().startsWith("jibun"))
                .map(FileSystemResource::new)
                .toArray(Resource[]::new);

        reader.setResources(landLotAddressFileResources);
        reader.setDelegate(itemReader);

        return reader;
    }

    /**
     * 지번주소 ItemProcessor
     * TODO 개발
     * @return 지번주소 ItemProcessor
     */
    @Bean
    public ItemProcessor<LandLotAddressItem, LandLotAddress> landLotAddressItemProcessor() {
        return null;
    }

    /**
     * 지번주소 ItemWriter
     * TODO 개발
     * @return 지번주소 ItemWriter
     */
    @Bean
    public ItemWriter<LandLotAddress> landLotAddressItemWriter() {
        return null;
    }
}
