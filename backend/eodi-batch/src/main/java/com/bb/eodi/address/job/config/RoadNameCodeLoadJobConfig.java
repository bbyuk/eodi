package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.entity.RoadNameCode;
import com.bb.eodi.address.domain.repository.RoadNameCodeRepository;
import com.bb.eodi.address.job.dto.RoadNameCodeItem;
import com.bb.eodi.address.job.reader.RoadNameCodeItemReader;
import com.bb.eodi.core.EodiBatchProperties;
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
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RoadNameCodeLoadJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;

    private final RoadNameCodeRepository roadNameCodeRepository;

    /**
     * 도로명코드 적재 배치 job
     *
     * 도로명코드 전체분 파일을 일괄 load 한다.
     * DB 초기 구축시 사용
     *
     * @param roadNameCodeLoadStep 도로명코드 데이터 적재 step
     * @return 도로명코드 적재 job
     */
    @Bean
    public Job roadNameCodeLoadJob(Step roadNameCodeLoadStep) {
        return new JobBuilder("roadNameCodeLoadJob", jobRepository)
                .start(roadNameCodeLoadStep)
                .build();
    }

    /**
     * 도로명코드 적재 step
     * @return 도로명코드 파일을 읽어 DB에 chunka 방식으로 write하는 step
     */
    @Bean
    public Step roadNameCodeLoadStep(
            ItemStreamReader<RoadNameCodeItem> roadNameCodeItemReader,
            ItemProcessor<RoadNameCodeItem, RoadNameCode> roadNameCodeItemProcessor,
            ItemWriter<RoadNameCode> roadNameCodeItemWriter
    ) {
        return new StepBuilder("roadNameCodeLoadStep", jobRepository)
                .<RoadNameCodeItem, RoadNameCode>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(roadNameCodeItemReader)
                .processor(roadNameCodeItemProcessor)
                .writer(roadNameCodeItemWriter)
                .build();
    }


    /**
     * 도로명코드 ItemReader
     * @param targetFilePath jobParameter -> 대상 파일 경로
     * @return 도로명코드 ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<RoadNameCodeItem> roadNameCodeItemReader(@Value("#{jobParameters['target-file-path']}") String targetFilePath) {
        return new RoadNameCodeItemReader(targetFilePath);
    }

    /**
     * 도로명코드 ItemProcessor
     * @return 도로명코드 ItemProcessor
     */
    @Bean
    @StepScope
    public ItemProcessor<RoadNameCodeItem, RoadNameCode> roadNameCodeItemProcessor() {
        return (item) -> RoadNameCode
                .builder()
                .roadNameCode(item.getRoadNameCode())
                .roadName(item.getRoadName())
                .engRoadName(item.getEngRoadName())
                .umdSeq(item.getUmdSeq())
                .sidoName(item.getSidoName())
                .engSidoName(item.getEngSidoName())
                .umdName(item.getUmdName())
                .engUmdName(item.getEngUmdName())
                .umdType(item.getUmdType())
                .umdCode(item.getUmdCode())
                .enabled(item.getEnabled())
                .changeCode(item.getChangeCode())
                .entranceDate(item.getEntranceDate())
                .revocationDate(item.getRevocationDate())
                .build();
    }

    /**
     * 도로명코드 ItemWriter
     * @return 도로명코드 ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<RoadNameCode> roadNameCodeItemWriter() {
        return chunk -> roadNameCodeRepository.insertBatch(chunk.getItems());
    }

}
