package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.service.AddressLinkageApiCallService;
import com.bb.eodi.address.job.dto.LandLotAddressItem;
import com.bb.eodi.address.job.dto.RoadNameAddressItem;
import com.bb.eodi.address.job.reader.LandLotAddressItemReader;
import com.bb.eodi.address.job.reader.RoadNameAddressItemReader;
import com.bb.eodi.address.job.tasklet.AddressLinkageApiCallTasklet;
import com.bb.eodi.address.job.tasklet.AddressLinkageFileUnzipTasklet;
import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

import static com.bb.eodi.address.domain.service.AddressLinkageTarget.ROAD_NAME_ADDRESS_KOR;


/**
 * 도로명주소 일변동 적용 일배치 job config
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AddressUpdateJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");


    /**
     * 도로명주소 일변동 적용 최신화 배치 Job
     * - 매일 수행 예정
     * - 이미 반영된 경우 skip 처리
     *
     * @param addressUpdateMainFlow 주소 업데이트 배치 main flow
     * @param addressPositionUpdateMainFlow 주소 위치정보 매핑 main flow
     * @return 도로명주소 일변동 적용 일배치 Job
     */
    @Bean
    public Job roadNameAddressUpdateJob(
            Flow addressUpdateMainFlow,
            Flow addressPositionUpdateMainFlow
    ) {
        return new JobBuilder("addressUpdateJob", jobRepository)
                .start(addressUpdateMainFlow)
                .next(addressPositionUpdateMainFlow)
                .end()
                .build();
    }

    /**
     * 주소 업데이트 배치 main flow
     *
     * @param addressLinkageApiCallStep   주소연계 API 호출 Step (변동분 파일 다운로드)
     * @param addressLinkageFileUnzipStep 주소연계 API 호출 후 다운로드 받은 파일의 압축을 해제한다.
     * @param addressUpdateFlow           일단위 주소 최신화 flow
     * @param tempFileDeleteStep          주소연계 API 호출을 통해 다운로드 받은 파일을 전체 삭제한다.
     * @return
     */
    @Bean
    public Flow addressUpdateMainFlow(
            Step addressLinkageApiCallStep,
            Step addressLinkageFileUnzipStep,
            Flow addressUpdateFlow,
            Step tempFileDeleteStep
    ) {
        return new FlowBuilder<Flow>("addressUpdateMainFlow")
                .start(addressLinkageApiCallStep)
                .on("SKIP").end()
                .from(addressLinkageApiCallStep)
                .on("COMPLETED").to(addressLinkageFileUnzipStep)
                // Step → Flow
                .from(addressLinkageFileUnzipStep)
                .on("COMPLETED").to(addressUpdateFlow)

                // Flow → Step
                .from(addressUpdateFlow)
                .on("COMPLETED").to(tempFileDeleteStep)

                .end();
    }

    /**
     * 주소 연계 API 호출 Step
     *
     * @param addressLinkageApiCallService 주소 연계 API 요청 서비스 컴포넌트
     * @param targetDirectory              연계 파일 다운로드 대상 디렉터리
     * @return 주소 연계 API 호출 Step
     */
    @Bean
    @JobScope
    public Step addressLinkageApiCallStep(
            AddressLinkageApiCallService addressLinkageApiCallService,
            @Value("#{jobParameters['target-directory']}") String targetDirectory) {
        return new StepBuilder("addressLinkageApiCallStep", jobRepository)
                .tasklet(new AddressLinkageApiCallTasklet(
                        ROAD_NAME_ADDRESS_KOR,
                        addressLinkageApiCallService,
                        targetDirectory
                ), transactionManager)
                .build();
    }

    /**
     * 주소 연계 API를 통해 다운로드 받은 파일을 모두 unzip 하는 step
     *
     * @param targetDirectory 재귀적으로 전체 unzip할 대상 디렉터리 - jobParameter
     * @return 주소 연계 API를 통해 다운로드 받은 파일을 모두 unzip 하는 step
     */
    @Bean
    @JobScope
    public Step addressLinkageFileUnzipStep(
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        return new StepBuilder("addressLinkageFileUnzipStep", jobRepository)
                .tasklet(new AddressLinkageFileUnzipTasklet(targetDirectory), transactionManager)
                .build();
    }


    /**
     * 주소 일단위 최신화 flow
     *
     * @param roadNameAddressUpdateStep  도로명주소 update step
     * @param landLotAddressUpdateStep   관련지번 update step
     * @param referenceVersionUpdateStep 기준정보버전 update step
     * @param targetDateDecider          대상일자 execution decider
     * @return 주소 일단위 최신화 flow
     */
    @Bean
    public Flow addressUpdateFlow(
            Step roadNameAddressUpdateStep,
            Step landLotAddressUpdateStep,
            Step referenceVersionUpdateStep,
            JobExecutionDecider targetDateDecider
    ) {
        return new FlowBuilder<Flow>("addressUpdateFlow")
                .start(roadNameAddressUpdateStep)
                .next(landLotAddressUpdateStep)
                .next(referenceVersionUpdateStep)
                .next(targetDateDecider)
                .on("CONTINUE").to(roadNameAddressUpdateStep)
                .from(targetDateDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 도로명주소 일변동분 반영 step
     * <p>
     *
     * @return 도로명주소 일변동분 반영 step
     */
    @Bean
    public Step roadNameAddressUpdateStep(
            ItemStreamReader<RoadNameAddressItem> roadNameAddressUpdateItemReader,
            ItemProcessor<RoadNameAddressItem, RoadNameAddress> roadNameAddressUpdateItemProcessor,
            ItemWriter<RoadNameAddress> roadNameAddressUpdateItemWriter
    ) {
        return new StepBuilder("roadNameAddressUpdateStep", jobRepository)
                .<RoadNameAddressItem, RoadNameAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(roadNameAddressUpdateItemReader)
                .processor(roadNameAddressUpdateItemProcessor)
                .writer(roadNameAddressUpdateItemWriter)
                .stream(roadNameAddressUpdateItemReader)
                .build();
    }

    /**
     * 도로명주소 최신화 배치 ItemReader
     *
     * @param targetDate 대상일자
     * @return 도로명주소 최신화 배치 ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<RoadNameAddressItem> roadNameAddressUpdateItemReader(
            @Value("#{jobExecutionContext['targetDate']}") LocalDate targetDate,
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        File targetFile = Arrays.stream(
                        Objects.requireNonNull(Paths.get(targetDirectory).resolve(targetDate.format(dtf)).toFile()
                                .listFiles(file -> file.getName().endsWith("TH_SGCO_RNADR_MST.TXT")))).findFirst()
                .orElseThrow(() -> new RuntimeException("파일을 찾지 못했습니다."));

        return new RoadNameAddressItemReader(targetFile.getAbsolutePath());
    }

    /**
     * 관련지번 일변동분 반영 step
     * <p>
     *
     * @param landLotAddressUpdateItemReader    관련지번 일변동분 ItemReader
     * @param landLotAddressUpdateItemProcessor 관련지번 일변동분 ItemProcessor
     * @param landLotAddressUpdateItemWriter    관련지번 일변동분 ItemWriter
     * @return 관련지번 일변동분 반영 step
     */
    @Bean
    public Step landLotAddressUpdateStep(
            ItemStreamReader<LandLotAddressItem> landLotAddressUpdateItemReader,
            ItemProcessor<LandLotAddressItem, LandLotAddress> landLotAddressUpdateItemProcessor,
            ItemWriter<LandLotAddress> landLotAddressUpdateItemWriter
    ) {
        return new StepBuilder("landLotAddressUpdateStep", jobRepository)
                .<LandLotAddressItem, LandLotAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(landLotAddressUpdateItemReader)
                .processor(landLotAddressUpdateItemProcessor)
                .writer(landLotAddressUpdateItemWriter)
                .stream(landLotAddressUpdateItemReader)
                .build();
    }

    /**
     * 도로명주소 연계 최신화 배치 관련지번 ItemReader
     *
     * @param targetDate 대상 date
     * @return 도로명주소 연계 최신화 배치 관련지번 ItemReader
     */
    @Bean
    @StepScope
    public ItemStreamReader<LandLotAddressItem> landLotAddressUpdateItemReader(
            @Value("#{jobExecutionContext['targetDate']}") LocalDate targetDate,
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        File targetFile = Arrays.stream(
                        Objects.requireNonNull(Paths.get(targetDirectory).resolve(targetDate.format(dtf)).toFile()
                                .listFiles(file -> file.getName().endsWith("TH_SGCO_RNADR_LNBR.TXT")))).findFirst()
                .orElseThrow(() -> new RuntimeException("파일을 찾지 못했습니다."));

        return new LandLotAddressItemReader(targetFile.getAbsolutePath());
    }

    /**
     * 기준정보버전 업데이트 step
     *
     * @param referenceVersionRepository 기준정보버전 repository bean
     * @return 기준정보버전 업데이트 step
     */
    @Bean
    public Step referenceVersionUpdateStep(
            ReferenceVersionRepository referenceVersionRepository) {
        return new StepBuilder("roadNameAddressReferenceVersionUpdateStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    ExecutionContext jobCtx =
                            contribution.getStepExecution()
                                    .getJobExecution()
                                    .getExecutionContext();

                    LocalDate targetDate = (LocalDate) jobCtx.get("targetDate");
                    referenceVersionRepository.updateEffectiveDateByReferenceVersionName(
                            targetDate,
                            ROAD_NAME_ADDRESS_KOR.getReferenceVersionName());
                    jobCtx.put("targetDate", Objects.requireNonNull(targetDate).plusDays(1));

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    /**
     * 임시 파일 삭제 Step
     *
     * @param addressLinkageFileDeleteTasklet 주소 연계 API를 통해 다운로드받은 임시파일을 삭제한다.
     * @return 임시 파일 삭제 Step
     */
    @Bean
    public Step tempFileDeleteStep(
            Tasklet addressLinkageFileDeleteTasklet
    ) {
        return new StepBuilder("tempFileDeleteStep", jobRepository)
                .tasklet(addressLinkageFileDeleteTasklet, transactionManager)
                .build();
    }
}