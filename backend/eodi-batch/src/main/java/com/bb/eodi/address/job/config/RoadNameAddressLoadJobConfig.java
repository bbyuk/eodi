package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.dto.RoadNameAddressItem;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.job.reader.RoadNameAddressItemReader;
import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.address.infrastructure.persistence.jpa.RoadNameAddressJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
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
 * 도로명 주소 적재 배치 jobConfig
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RoadNameAddressLoadJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;

    private final RoadNameAddressJpaRepository roadNameAddressJpaRepository;
    /**
     * 도로명주소 적재 job
     *
     * 도로명주소 전체분 파일을 일괄 load한다.
     * DB초기 구축시 사용
     *
     * @param roadNameAddressLoadStep 도로명주소 데이터를 로드해 DB에 적재하는 Step
     * @return 도로명주소 적재 job
     */
    @Bean
    public Job roadNameAddressLoadJob(Step roadNameAddressLoadStep) {
        return new JobBuilder("roadNameAddressLoadJob", jobRepository)
                .start(roadNameAddressLoadStep)
                .build();
    }

    @Bean
    public Step roadNameAddressLoadStep(
            ItemReader<RoadNameAddressItem> multiResourceRoadNameAddressItemReader,
            ItemProcessor<RoadNameAddressItem, RoadNameAddress> roadNameAddressItemProcessor,
            ItemWriter<RoadNameAddress> roadNameAddressItemWriter
    ) {
        return new StepBuilder("roadNameAddressLoadStep", jobRepository)
                .<RoadNameAddressItem, RoadNameAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(multiResourceRoadNameAddressItemReader)
                .processor(roadNameAddressItemProcessor)
                .writer(roadNameAddressItemWriter)
                .build();
    }

    /**
     * 도로명주소 멀티 리소스 reader
     * @param targetDirectoryPath 대상 directory 경로
     * @return 도로명주소 멀티 리소스 reader
     */
    @Bean
    @StepScope
    public MultiResourceItemReader<RoadNameAddressItem> multiResourceRoadNameAddressItemReader(
            @Value("#{jobParameters['target-directory']}") String targetDirectoryPath
    ) {
        MultiResourceItemReader<RoadNameAddressItem> reader = new MultiResourceItemReader<>();

        File directory = new File(targetDirectoryPath);
        Resource[] roadNameAddressFileResources = Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                .filter(file -> file.getName().startsWith("road"))
                .map(FileSystemResource::new)
                .toArray(Resource[]::new);

        reader.setResources(roadNameAddressFileResources);
        reader.setDelegate(new RoadNameAddressItemReader());

        return reader;
    }


    /**
     * 도로명주소 ItemProcessor
     * TODO ItemProcessor 개발 및 파일 분리
     * @return 도로명주소 ItemProcessor
     */
    @Bean
    @StepScope
    public ItemProcessor<RoadNameAddressItem, RoadNameAddress> roadNameAddressItemProcessor() {
        return item -> RoadNameAddress.builder()
                .roadNameCode(item.getSigunguCode() + item.getRoadNameNo())
                .sigunguCode(item.getSigunguCode())
                .roadNameNo(item.getRoadNameNo())
                .roadName(item.getRoadName())
                .engRoadName(item.getEngRoadName())
                .umdSeq(item.getUmdSeq())
                .sidoName(item.getSidoName())
                .sigunguName(item.getSigunguName())
                .umdGb(item.getUmdGb())
                .umdCode(item.getUmdCode())
                .umdName(item.getUmdName())
                .parentRoadNameNo(item.getParentRoadNameNo())
                .parentRoadName(item.getParentRoadName())
                .useYn(item.getUseYn())
                .changeHistoryReason(item.getChangeHistoryReason())
                .changeHistoryInfo(item.getChangeHistoryInfo())
                .engSidoName(item.getEngSidoName())
                .engSigunguName(item.getEngSigunguName())
                .engUmdName(item.getEngUmdName())
                .announcementDate(item.getAnnouncementDate())
                .expirationDate(item.getExpirationDate())
                .build();
    }

    /**
     * 도로명주소 ItemWriter
     * TODO ItemWriter 개발 및 파일 분리
     * @return 도로명주소 ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<RoadNameAddress> roadNameAddressItemWriter() {
        return item -> {
            roadNameAddressJpaRepository.saveAll(item.getItems());
        };
    }

}
