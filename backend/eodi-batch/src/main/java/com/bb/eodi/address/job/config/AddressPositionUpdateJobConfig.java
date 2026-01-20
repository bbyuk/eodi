package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.service.AddressLinkageApiCallService;
import com.bb.eodi.address.job.dto.AddressPositionItem;
import com.bb.eodi.address.job.tasklet.AddressLinkageApiCallTasklet;
import com.bb.eodi.address.job.tasklet.AddressLinkageFileUnzipTasklet;
import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.bb.eodi.address.domain.service.AddressLinkageTarget.ADDRESS_ENTRANCE;

/**
 * 주소 위치정보 Update Job Config
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AddressPositionUpdateJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");

    /**
     * 주소 위치정보 update Job Config
     *
     * @param addressEntranceLinkageApiCallStep     주소 출입구 연계 API 요청 Step
     * @param addressEntranceLinkageFileUnzipStep   주소 출입구 연계 API 다운로드 파일 압축 해제 Step
     * @param addressPositionUpdateFlow             주소 위치 정보 Update flow
     * @param addressLinkageFileDeleteStep          주소 출입구 연계 API 다운로드 파일 삭제 Step
     *
     * @return
     */
    @Bean
    public Job addressPositionUpdateJob(
            Step addressEntranceLinkageApiCallStep,
            Step addressEntranceLinkageFileUnzipStep,
            Flow addressPositionUpdateFlow,
            Step addressLinkageFileDeleteStep
    ) {
        Flow mainFlow = new FlowBuilder<Flow>("addressPositionUpdateMainFlow")
                .start(addressEntranceLinkageApiCallStep)
                .next(addressEntranceLinkageFileUnzipStep)
                .from(addressEntranceLinkageFileUnzipStep)
                .on("COMPLETED").to(addressPositionUpdateFlow)
                .from(addressPositionUpdateFlow)
                .on("COMPLETED").to(addressLinkageFileDeleteStep)
                .end();


        return new JobBuilder("addressPositionUpdateJob", jobRepository)
                .start(mainFlow)
                .end()
                .build();
    }

    /**
     * 주소 출입구 정보 연계 API 요청 Step
     *
     * @param addressLinkageApiCallService 주소 연계 API 요청 서비스 컴포넌트
     * @param targetDirectory              연계 파일 다운로드 대상 디렉터리
     * @return 주소 출입구 정보 연계 API 요청 Step
     */
    @Bean
    @JobScope
    public Step addressEntranceLinkageApiCallStep(
            AddressLinkageApiCallService addressLinkageApiCallService,
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        return new StepBuilder("addressEntranceLinkageApiCallStep", jobRepository)
                .tasklet(new AddressLinkageApiCallTasklet(
                        ADDRESS_ENTRANCE,
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
    public Step addressEntranceLinkageFileUnzipStep(
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        return new StepBuilder("addressLinkageFileUnzipStep", jobRepository)
                .tasklet(new AddressLinkageFileUnzipTasklet(targetDirectory), transactionManager)
                .build();
    }

    /**
     * 주소 출입구 정보 연계 데이터로 주소 좌표 정보 update flow
     *
     * @param addressPositionUpdateStep                 주소 좌표 정보 update step
     * @param addressEntranceReferenceVersionUpdateStep 주소 좌표 기준정보 버전 update step
     * @param targetDateDecider                         대상일자 execution decider
     * @return 주소 위치 정보 일단위 최신화 flow
     */
    @Bean
    public Flow addressPositionUpdateFlow(
            Step addressPositionUpdateStep,
            Step addressEntranceReferenceVersionUpdateStep,
            JobExecutionDecider targetDateDecider
    ) {
        return new FlowBuilder<Flow>("addressPositionUpdateFlow")
                .start(addressPositionUpdateStep)
                .next(addressEntranceReferenceVersionUpdateStep)
                .next(targetDateDecider)
                .on("CONTINUE").to(addressPositionUpdateStep)
                .from(targetDateDecider)
                .on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build();
    }

    /**
     * 주소 위치 정보 일변동분 반영 Step
     *
     * @param addressPositionUpdateItemReader    주소 위치 정보 update ItemReader
     * @param addressPositionUpdateItemProcessor 주소 위치 정보 mapping ItemProcessor
     * @param addressPositionUpdateItemWriter    주소 위치 정보 update ItemWriter
     * @return 주소 위치 정보 일변동분 반영 Step
     */
    @Bean
    public Step addressPositionUpdateStep(
            ItemStreamReader<AddressPositionItem> addressPositionUpdateItemReader,
            ItemProcessor<AddressPositionItem, RoadNameAddress> addressPositionUpdateItemProcessor,
            ItemWriter<RoadNameAddress> addressPositionUpdateItemWriter
    ) {
        return new StepBuilder("addressPositionUpdateStep", jobRepository)
                .<AddressPositionItem, RoadNameAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(addressPositionUpdateItemReader)
                .processor(addressPositionUpdateItemProcessor)
                .writer(addressPositionUpdateItemWriter)
                .stream(addressPositionUpdateItemReader)
                .build();
    }

    /**
     * 기준정보버전 업데이트 step
     *
     * @param referenceVersionRepository 기준정보버전 repository bean
     * @return 기준정보버전 업데이트 step
     */
    @Bean
    public Step addressEntranceReferenceVersionUpdateStep(
            ReferenceVersionRepository referenceVersionRepository) {
        return new StepBuilder("addressEntranceReferenceVersionUpdateStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    ExecutionContext jobCtx =
                            contribution.getStepExecution()
                                    .getJobExecution()
                                    .getExecutionContext();

                    LocalDate targetDate = (LocalDate) jobCtx.get("targetDate");
                    referenceVersionRepository.updateEffectiveDateByReferenceVersionName(
                            targetDate,
                            ADDRESS_ENTRANCE.getReferenceVersionName()
                    );
                    jobCtx.put("targetDate", Objects.requireNonNull(targetDate).plusDays(1));

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    /**
     * 주소 연계 File 삭제 Step
     * @param addressLinkageFileDeleteTasklet 주소 연계 파일 삭제 Tasklet
     * @return 주소 연계 File 삭제 Step
     */
    @Bean
    public Step addressLinkageFileDeleteStep(Tasklet addressLinkageFileDeleteTasklet) {
        return new StepBuilder("addressLinkageFileDeleteStep", jobRepository)
                .tasklet(addressLinkageFileDeleteTasklet, transactionManager)
                .build();
    }


}
